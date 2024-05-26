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

  // 회원가입 인증
  CERTIFICATION("KOALA 이메일 인증 코드", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html"),

  // 지원 전형 안내
  DOCUMENT_PASS("KOALA 서류 전형 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/document-pass.html"),
  DOCUMENT_FAIL("KOALA 서류 전형 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/document-fail.html"),
  INTERVIEW("KOALA 면접 일정 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/interview.html"),
  FAIL("KOALA 면접 결과 발표", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/fail.html"),
  PASS("KOALA 면접 결과 발표", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/pass.html"),

  // 비밀번호 찾기
  // todo html
  FIND_PASSWORD("KOALA 비밀번호 안내", "https://kau-koala.s3.ap-northeast-2.amazonaws.com/certification.html");

  private String subject;
  private String path;

  public static boolean exist(String emailType) {
    return Arrays.stream(values()).anyMatch(type -> type.toString().equals(emailType));
  }

  public static String getSubject(String emailType) {
    return Arrays.stream(EmailType.values())
      .filter(type -> type.toString().equals(emailType))
      .findFirst()
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "Not Found Enum Value : " + emailType))
      .subject;
  }
}
