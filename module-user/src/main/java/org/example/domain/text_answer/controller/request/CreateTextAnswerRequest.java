package org.example.domain.text_answer.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "주관식 문항 답변 요청 객체")
public record CreateTextAnswerRequest(

  @NotNull
  @Schema(description = "주관식 문항 ID")
  Long textQuestionId,

  @NotBlank
  @Schema(description = "주관식 답변 내용", example = "제가 최고입니다.")
  String text
) {

}
