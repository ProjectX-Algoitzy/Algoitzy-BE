package org.example.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateApplicationRequest(

  @NotNull
  @Schema(description = "지원서 대상 스터디 ID", example = "1")
  long studyId,
  @NotBlank
  @Schema(description = "지원서 제목", example = "지원서 제목")
  String title
) {

}
