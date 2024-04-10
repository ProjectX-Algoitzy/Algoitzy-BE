package org.example.domain.select_question.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateSelectQuestionRequest(

  @NotBlank
  @Schema(description = "객관식 질문", example = "가능한 면접 일자를 선택해주세요.")
  String question
) {
}
