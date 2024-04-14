package org.example.domain.select_answer.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateSelectAnswerRequest(

  @NotNull
  @Schema(description = "필드 id")
  Long fieldId
) {

}
