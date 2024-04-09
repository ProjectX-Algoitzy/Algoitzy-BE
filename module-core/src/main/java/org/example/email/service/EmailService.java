package org.example.email.service;

import static org.example.email.enums.EmailType.CERTIFICATION;

import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.email.controller.request.CertificationEmailRequest;
import org.example.email.enums.EmailType;
import org.example.util.RandomUtils;
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

  public void sendCertificationEmail(CertificationEmailRequest request) {
    String code = RandomUtils.getRandomNumber();
    redisTemplate.opsForValue().set(request.email(), code);
    String html = htmlToString(CERTIFICATION.getPath()).replace("${code}", code);

    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
      helper.setTo(request.email());
      helper.setSubject(CERTIFICATION.getSubject());
      helper.setText(html, true);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "인증코드 전송 중 오류가 발생했습니다.");
    }
  }

  private String htmlToString(String path) {
    String absolutePath = System.getProperty("user.dir") + File.separator + path;
    StringBuilder html = new StringBuilder();
    try {
      BufferedReader in = new BufferedReader(new FileReader(absolutePath));
      String line;
      while ((line = in.readLine()) != null) {
        html.append(line);
      }
      in.close();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "HTML을 String으로 변환 중 오류가 발생했습니다.");
    }

    return html.toString();
  }

}
