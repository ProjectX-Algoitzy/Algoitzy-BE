package org.example.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EmailType {
  CERTIFICATION("KOALA 이메일 인증 코드", "module-core/src/main/java/org/example/email/html/certification.html"),
  DOCUMENT_RESULT("KOALA 서류 전형 안내", "module-core/src/main/java/org/example/email/html/certification.html"),
  INTERVIEW_DATE("KOALA 면접 일정 안내", "module-core/src/main/java/org/example/email/html/certification.html"),
  INTERVIEW_RESULT("KOALA 면접 결과 발표", "module-core/src/main/java/org/example/email/html/certification.html");

  private String subject;
  private String path;
}
