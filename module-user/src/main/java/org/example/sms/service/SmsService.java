package org.example.sms.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.example.api_response.ApiResponse;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.sms.controller.request.CertificationPhoneNumberRequest;
import org.example.util.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  private final StringRedisTemplate redisTemplate;

  private static final int MAX_REQUESTS_PER_DAY = 5;

  private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

  public void sendCertificationPhoneNumber(CertificationPhoneNumberRequest request, String ipAddress) {
    if (!tryConsumeBucket(ipAddress)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "SMS 인증 요청 횟수를 초과하였습니다.");
    }
    sendCertificationSms(request);
  }

  private void sendCertificationSms(CertificationPhoneNumberRequest request) {
    Message message = new Message(apiKey, apiSecret);

    String code = RandomUtils.getRandomNumber();
    redisTemplate.opsForValue().set(request.phoneNumber(), code, Duration.ofSeconds(180));

    HashMap<String, String> params = new HashMap<>();
    params.put("from", fromNumber);
    params.put("type", "SMS");
    params.put("to", request.phoneNumber());
    params.put("text", "KOALA 인증번호는 " + code + " 입니다.");

    try {
      message.send(params);
    } catch (CoolsmsException e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "SMS 인증코드 전송 중 오류가 발생했습니다.");
    }
  }

  private boolean tryConsumeBucket(String ipAddress) {
    // 해당 IP 주소에 대한 Bucket 가져오기
    Bucket bucket = buckets.computeIfAbsent(ipAddress, key -> {
      Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_DAY, Refill.intervally(MAX_REQUESTS_PER_DAY, Duration.ofDays(1)));
      return Bucket.builder()
          .addLimit(limit)
          .build();
    });

    return bucket.tryConsume(1);
  }

}
