package org.example.domain.study_member.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StudyMemberStatus {

  DOCUMENT("서류 전형"),
  DOCUMENT_PASS("서류 합격"),
  DOCUMENT_FAIL("서류 탈락"),
  INTERVIEW("면접 전형"),
  FAIL("불합격"),
  PASS("최종 합격");

  private final String status;
}
