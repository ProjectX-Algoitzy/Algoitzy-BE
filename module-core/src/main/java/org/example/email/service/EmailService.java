package org.example.email.service;

import static org.example.email.enums.EmailType.CERTIFICATION;

import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.email.controller.request.CertificationEmailRequest;
import org.example.util.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EmailService {

  private final StringRedisTemplate redisTemplate;
  private final JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  private String mailFrom;

  public void sendCertificationEmail(CertificationEmailRequest request) {
    String code = RandomUtils.getRandomNumber();
    redisTemplate.opsForValue().set(request.email(), code, Duration.ofSeconds(180));
    String html = htmlToString(CERTIFICATION.getPath()).replace("${code}", code);

    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      helper.setFrom(mailFrom);
      helper.setTo(request.email());
      helper.setSubject(CERTIFICATION.getSubject());
      helper.setText(html, true);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "Email 인증코드 전송 중 오류가 발생했습니다 : " + e.getMessage());
    }
  }

  private String htmlToString(String path) {
    StringBuilder html = new StringBuilder();

    try {
      URL url = new URL(path);
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        html.append(line);
      }
      reader.close();
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "HTML을 String으로 변환 중 오류가 발생했습니다.");
    }

    return html.toString();
  }

}
