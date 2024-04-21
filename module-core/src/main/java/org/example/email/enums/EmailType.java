package org.example.email.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EmailType {
  CERTIFICATION("KOALA 이메일 인증 코드", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html"),
  DOCUMENT_RESULT("KOALA 서류 전형 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html"),
  INTERVIEW_DATE("KOALA 면접 일정 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html"),
  INTERVIEW_RESULT("KOALA 면접 결과 발표", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html");

  private String subject;
  private String path;

  public static String getSubject(String emailType) {
    return Arrays.stream(EmailType.values())
      .filter(type -> type.subject.equals(emailType))
      .findFirst().orElseThrow(() ->
        new GeneralException(ErrorStatus.BAD_REQUEST, "Email Type이 존재하지 않습니다."))
      .subject;
  }
}
