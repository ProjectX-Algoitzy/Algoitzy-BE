package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "지원서 양식 생성 요청 객체")
public record CreateApplicationRequest(

  @NotNull
  @Schema(description = "지원서 대상 스터디 ID")
  Long studyId

) {
}
