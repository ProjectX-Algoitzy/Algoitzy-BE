package org.example.domain.select_question.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.example.domain.field.controller.request.UpdateFieldRequest;

@Schema(description = "객관식 문항 생성 요청 객체")
public record UpdateSelectQuestionRequest(

  @NotBlank
  @Schema(description = "객관식 질문", example = "가능한 면접 일자를 선택해주세요.")
  String question,

  @Schema(description = "필수 여부", example = "true")
  boolean isRequired,

  @Schema(description = "다중 선택 가능 여부", example = "true")
  boolean isMultiSelect,

  @Schema(description = "문항 번호")
  int sequence,

  @Schema(description = "객관식 필드")
  List<@Valid UpdateFieldRequest> updateFieldRequestList
) {
}
