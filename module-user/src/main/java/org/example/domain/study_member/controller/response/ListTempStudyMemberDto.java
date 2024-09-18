package org.example.domain.study_member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.study_member.enums.StudyMemberStatus;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "자율 스터디원 목록 응답 DTO")
public class ListTempStudyMemberDto {

  @Schema(description = "스터디원 ID")
  private long studyMemberId;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "휴대폰 번호")
  private String phoneNumber;

  @Schema(description = "상태")
  private StudyMemberStatus status;

  public void blindPhoneNumber() {
    this.phoneNumber = "권한 없음";
  }
}
