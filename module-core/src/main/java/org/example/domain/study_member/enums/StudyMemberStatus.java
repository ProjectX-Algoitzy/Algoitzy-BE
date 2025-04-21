package org.example.domain.study_member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyMemberStatus {

  DOCUMENT("서류 전형", 1),
  DOCUMENT_PASS("서류 합격", 2),
  DOCUMENT_FAIL("서류 탈락", 2),
  TEMP_APPLY("지원", 3), // 자율 스터디
  INTERVIEW("면접 전형", 3),
  FAIL("불합격", 4),
  PASS("최종 합격", 4);

  private final String status;
  private final int order;

}
