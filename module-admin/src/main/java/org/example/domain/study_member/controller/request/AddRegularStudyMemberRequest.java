package org.example.domain.study_member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "정규 스터디원 추가 요청 객체")
public record AddRegularStudyMemberRequest(

  @NotNull
  @Schema(description = "멤버 ID")
  Long memberId

) {

}
