package org.example.domain.text_question.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateTextQuestionRequest(

  @NotBlank
  @Schema(description = "주관식 문항", example = "KOALA에 지원한 계기를 작성해주세요. (300자)")
  String question
) {

}
