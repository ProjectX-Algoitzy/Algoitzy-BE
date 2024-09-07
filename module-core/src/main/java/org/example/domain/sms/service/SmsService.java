package org.example.domain.sms.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.sms.controller.request.CertificationPhoneNumberRequest;
import org.example.domain.sms.controller.request.ValidatePhoneNumberRequest;
import org.example.util.RandomUtils;
import org.example.util.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SmsService {

  @Value("${coolsms.apikey}")
  private String apiKey;

  @Value("${coolsms.apisecret}")
  private String apiSecret;

  @Value("${coolsms.fromnumber}")
  private String fromNumber;

  private final RedisUtils redisUtils;

  private static final int MAX_REQUESTS_PER_DAY = 5;

  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  public String sendCertificationPhoneNumber(CertificationPhoneNumberRequest request) {
    if (!StringUtils.hasText(request.userRandomId())) {
      String userRandomId = RandomUtils.getRandomString(36);
      tryConsumeBucket(userRandomId);
      sendCertificationSms(request);
      return userRandomId;
    } else {
      if (!tryConsumeBucket(request.userRandomId())) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "SMS 인증 요청 횟수를 초과하였습니다.");
      }
      sendCertificationSms(request);
    }
    return request.userRandomId();
  }

  /*
  * 인증 번호 API 요청
  * */
  private void sendCertificationSms(CertificationPhoneNumberRequest request) {
    try {
      Message message = new Message(apiKey, apiSecret);
      String code = RandomUtils.getRandomNumber();
      redisUtils.saveWithExpireTime(request.phoneNumber(),  code, Duration.ofSeconds(180));

      HashMap<String, String> params = new HashMap<>();
      params.put("from", fromNumber);
      params.put("type", "SMS");
      params.put("to", request.phoneNumber());
      params.put("text", "KOALA 인증번호는 " + code + " 입니다.");

      message.send(params);
    } catch (CoolsmsException e) {
      log.error("SMS 인증코드 전송 중 오류가 발생했습니다.");
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "SMS 인증코드 전송 중 오류가 발생했습니다.");
    } catch (Exception e) {
      log.error("SMS 인증코드 전송 중 알 수 없는 오류가 발생했습니다.");
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "SMS 인증코드 전송 중 알 수 없는 오류가 발생했습니다.");
    }
  }

  /*
  * Bucket 토큰 소비
  * */
  private boolean tryConsumeBucket(String userRandomId) {
    Bucket bucket = buckets.computeIfAbsent(userRandomId, key -> {
      Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_DAY, Refill.intervally(MAX_REQUESTS_PER_DAY, Duration.ofDays(1)));
      return Bucket.builder()
        .addLimit(limit)
        .build();
    });
    return bucket.tryConsume(1);
  }

  /**
   * 휴대전화 인증 확인
   */
  public void validatePhoneNumber(ValidatePhoneNumberRequest request) {
    String code = Optional.ofNullable(redisUtils.getValue(request.phoneNumber()))
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "해당 번호와 매칭되는 인증코드가 없습니다."));

    if (!code.equals(request.code())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "인증코드가 일치하지 않습니다.");
    }
    redisUtils.delete(request.phoneNumber());
  }
}
