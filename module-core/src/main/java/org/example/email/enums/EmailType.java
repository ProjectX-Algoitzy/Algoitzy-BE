package org.example.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum EmailType {
  CERTIFICATION("이메일 인증 코드"),
  DOCUMENT_RESULT("서류 결과 발표"),
  INTERVIEW_DATE("면접 일정 안내"),
  INTERVIEW_RESULT("면접 결과 발표");

  private String subject;
}
