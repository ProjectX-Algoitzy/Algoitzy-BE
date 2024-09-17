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
  CERTIFICATION("KOALA 이메일 인증 코드", "certification.html"),

  // 지원 전형 안내
  DOCUMENT_PASS("KOALA 서류 전형 안내", "document-pass.html"),
  DOCUMENT_FAIL("KOALA 서류 전형 안내", "document-fail.html"),
  INTERVIEW("KOALA 면접 일정 안내", "interview.html"),
  FAIL("KOALA 면접 결과 발표", "fail.html"),
  PASS("KOALA 면접 결과 발표", "pass.html"),

  // 비밀번호 재설정
  FIND_PASSWORD("KOALA 비밀번호 안내", "reset-password.html"),

  // 스케쥴러
  ATTENDANCE_SCHEDULER("출석부 갱신 알림", ""),
  WORKBOOK_SCHEDULER("문제집 자동 생성 알림", ""),
  BAEKJOON_SCHEDULER("백준 문제 저장 알림", ""),

  // 자율 스터디
  TEMP_APPLY("자율 스터디 지원자 안내", "temp-apply.html"),
  TEMP_PASS("자율 스터디 수라 안내", "temp-pass.html");


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
