package org.example.domain.answer.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import org.example.domain.select_answer.controller.request.CreateSelectAnswerRequest;
import org.example.domain.text_answer.controller.request.CreateTextAnswerRequest;

public record CreateAnswerRequest(
  @Schema(description = "주관식 답변 생성 요청 list")
  List<@Valid CreateTextAnswerRequest> createTextAnswerRequestList,

  @Schema(description = "객관식 답변 생성 요청 list")
  List<@Valid CreateSelectAnswerRequest> createSelectAnswerRequestList
) {

}
