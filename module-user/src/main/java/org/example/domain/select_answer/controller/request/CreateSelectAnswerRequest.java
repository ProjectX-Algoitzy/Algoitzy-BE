package org.example.domain.select_answer.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateSelectAnswerRequest(

  @NotNull
  @Schema(description = "객관식 문항 id")
  Long selectQuestionId,

  @Schema(description = "필드 id")
  List<Long> fieldIdList
) {

}
