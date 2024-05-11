package org.example.email.service;

import static org.example.email.enums.EmailType.CERTIFICATION;
import static org.example.email.enums.EmailType.DOCUMENT_FAIL;
import static org.example.email.enums.EmailType.DOCUMENT_PASS;
import static org.example.email.enums.EmailType.FAIL;
import static org.example.email.enums.EmailType.INTERVIEW;
import static org.example.email.enums.EmailType.PASS;

import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.service.CoreMemberService;
import org.example.email.controller.request.SendEmailRequest;
import org.example.email.enums.EmailType;
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

  private final CoreMemberService coreMemberService;
  private final StringRedisTemplate redisTemplate;
  private final JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  private String mailFrom;

  public void sendEmail(SendEmailRequest request) {
    switch (EmailType.valueOf(request.type())) {
      case CERTIFICATION -> sendCertificateEmail(request);
      case DOCUMENT_PASS -> sendDocumentEmail(request, true);
      case DOCUMENT_FAIL -> sendDocumentEmail(request, false);
      case INTERVIEW -> sendInterviewEmail(request);
      case PASS -> sendResultEmail(request, true);
      case FAIL -> sendResultEmail(request, false);
    }
  }

  private void sendCertificateEmail(SendEmailRequest request) {
    String code = RandomUtils.getRandomNumber();
    redisTemplate.opsForValue().set(request.email(), code, Duration.ofSeconds(180));
    String html = htmlToString(CERTIFICATION.getPath()).replace("${code}", code);
    send(request, html);
  }

  private void sendDocumentEmail(SendEmailRequest request, boolean isPass) {
    String name = coreMemberService.findByEmail(request.email()).getName();
    String html;
    if (isPass) {
      html = htmlToString(DOCUMENT_PASS.getPath()).replace("${name}", name);
    } else {
      html = htmlToString(DOCUMENT_FAIL.getPath()).replace("${name}", name);
    }
    send(request, html);
  }

  private void sendInterviewEmail(SendEmailRequest request) {
    String name = coreMemberService.findByEmail(request.email()).getName();
    // todo time
    String html = htmlToString(INTERVIEW.getPath()).replace("${name}", name).replace("${time}", "time");
    send(request, html);
  }

  private void sendResultEmail(SendEmailRequest request, boolean isPass) {
    String name = coreMemberService.findByEmail(request.email()).getName();
    String html;
    if (isPass) {
      html = htmlToString(PASS.getPath()).replace("${name}", name);
    } else {
      html = htmlToString(FAIL.getPath()).replace("${name}", name);
    }
    send(request, html);
  }

  private void send(SendEmailRequest request, String html) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      helper.setFrom(mailFrom);
      helper.setTo(request.email());
      helper.setSubject(EmailType.getSubject(request.type()));
      helper.setText(html, true);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, request.type() + " EMAIL 전송 중 오류가 발생했습니다 : " + e.getMessage());
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
