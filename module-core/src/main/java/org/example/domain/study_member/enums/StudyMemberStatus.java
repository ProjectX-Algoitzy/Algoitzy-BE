package org.example.domain.study_member.enums;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;

@Getter
@AllArgsConstructor
public enum StudyMemberStatus {

  DOCUMENT("서류 전형", 1),
  DOCUMENT_PASS("서류 합격", 2),
  DOCUMENT_FAIL("서류 탈락", 2),
  INTERVIEW("면접 전형", 3),
  FAIL("불합격", 4),
  PASS("최종 합격", 4);

  private final String status;
  private final int order;

  public static StudyMemberStatus fromStatus(String status) {
    return Arrays.stream(values())
      .filter(value -> value.getStatus().equals(status))
      .findFirst()
      .orElseThrow(() -> new GeneralException(ErrorStatus.BAD_REQUEST, "Not Found Enum Value : " + status));
  }
}
