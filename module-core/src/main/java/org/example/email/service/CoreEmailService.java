package org.example.email.service;

import static org.example.domain.study_member.enums.StudyMemberStatus.*;
import static org.example.email.enums.EmailType.CERTIFICATION;
import static org.example.email.enums.EmailType.DOCUMENT_FAIL;
import static org.example.email.enums.EmailType.DOCUMENT_PASS;
import static org.example.email.enums.EmailType.FAIL;
import static org.example.email.enums.EmailType.FIND_PASSWORD;
import static org.example.email.enums.EmailType.INTERVIEW;
import static org.example.email.enums.EmailType.PASS;

import jakarta.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.interview.Interview;
import org.example.domain.interview.repository.InterviewRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.email.controller.request.SendEmailRequest;
import org.example.email.enums.EmailType;
import org.example.util.RandomUtils;
import org.example.util.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CoreEmailService {

  private final CoreMemberService coreMemberService;
  private final StudyMemberRepository studyMemberRepository;
  private final InterviewRepository interviewRepository;
  private final PasswordEncoder encoder;
  private final RedisUtils redisUtils;
  private final JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  private String mailFrom;
  @Value("${url.s3-email}")
  private String s3Url;

  public void sendEmail(SendEmailRequest request) {
    switch (EmailType.valueOf(request.type())) {
      case CERTIFICATION -> sendCertificateEmail(request);
      case DOCUMENT_PASS -> sendDocumentEmail(request, true);
      case DOCUMENT_FAIL -> sendDocumentEmail(request, false);
      case INTERVIEW -> sendInterviewEmail(request);
      case PASS -> sendResultEmail(request, true);
      case FAIL -> sendResultEmail(request, false);
      case FIND_PASSWORD -> sendPasswordEmail(request);
    }
  }

  private void sendCertificateEmail(SendEmailRequest request) {
    String code = RandomUtils.getRandomNumber();
    redisUtils.save(request.emailList().get(0), code, Duration.ofSeconds(180));
    String html = htmlToString(s3Url + CERTIFICATION.getPath()).replace("${code}", code);
    send(request.emailList().get(0), request.type(), html);
  }

  private void sendDocumentEmail(SendEmailRequest request, boolean isPass) {
    for (String email : request.emailList()) {
      Member member = coreMemberService.findByEmail(email);
      changeStatus(request, member);

      String html;
      if (isPass) {
        html = htmlToString(s3Url + DOCUMENT_PASS.getPath())
          .replace("${name}", member.getName())
          .replace("${phoneNumber}", coreMemberService.getOwner().getPhoneNumber());
      } else {
        html = htmlToString(s3Url + DOCUMENT_FAIL.getPath()).replace("${name}", member.getName());
      }
      send(email, request.type(), html);
    }
  }

  private void sendInterviewEmail(SendEmailRequest request) {
    for (String email : request.emailList()) {
      Member member = coreMemberService.findByEmail(email);
      StudyMember studyMember = studyMemberRepository.findByMemberAndStatus(member, StudyMemberStatus.DOCUMENT_PASS)
        .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "해당 회원은 서류 합격 단계가 아닙니다."));
      Interview interview = interviewRepository.findByStudyMember(studyMember)
        .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "해당 스터디원의 면접 일정이 존재하지 않습니다."));
      if (interview.getCreatedTime().isEqual(interview.getUpdatedTime())) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "면접 시간을 설정해주세요.");
      }
      changeStatus(request, member);

      String html = htmlToString(s3Url + INTERVIEW.getPath())
        .replace("${name}", member.getName())
        .replace("${time}", interview.getTime());
      send(email, request.type(), html);
    }
  }

  private void sendResultEmail(SendEmailRequest request, boolean isPass) {
    for (String email : request.emailList()) {
      Member member = coreMemberService.findByEmail(email);
      changeStatus(request, member);

      String html;
      if (isPass) {
        html = htmlToString(s3Url + PASS.getPath()).replace("${name}", member.getName());
      } else {
        html = htmlToString(s3Url + FAIL.getPath()).replace("${name}", member.getName());
      }
      send(email, request.type(), html);
    }
  }

  private void sendPasswordEmail(SendEmailRequest request) {
    String password = RandomUtils.getRandomString(11);
    String html = htmlToString(s3Url + FIND_PASSWORD.getPath()).replace("${password}", password);
    send(request.emailList().get(0), request.type(), html);

    Member member = coreMemberService.findByEmail(request.emailList().get(0));
    member.updatePassword(encoder.encode(password));
  }

  private void send(String email, String type, String html) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
      helper.setFrom(mailFrom);
      helper.setTo(email);
      helper.setSubject(EmailType.getSubject(type));
      helper.setText(html, true);
      mailSender.send(mimeMessage);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, type + " EMAIL 전송 중 오류가 발생했습니다 : " + e.getMessage());
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

  /**
   * 전형 단계 갱신
   */
  private void changeStatus(SendEmailRequest request, Member member) {
    List<StudyMember> studyMemberList = studyMemberRepository.findAllByMember(member);
    Optional<StudyMember> optionalStudyMember = studyMemberList.stream()
      .filter(studyMember -> studyMember.getStatus().getOrder() == valueOf(request.type()).getOrder() - 1)
      .findFirst();
    if (optionalStudyMember.isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "전형 단계를 확인해주세요.");
    }

    StudyMember studyMember = optionalStudyMember.get();
    studyMember.updateStatus(valueOf(request.type()));
  }
}
