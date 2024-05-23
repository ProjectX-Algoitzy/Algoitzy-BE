package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import org.example.domain.select_question.controller.request.UpdateSelectQuestionRequest;
import org.example.domain.text_question.controller.request.UpdateTextQuestionRequest;

@Schema(description = "지원서 양식 생성 요청 객체")
public record UpdateApplicationRequest(

  @NotNull
  @Schema(description = "지원서 대상 스터디 ID")
  Long studyId,

  @NotBlank
  @Schema(description = "지원서 제목", example = "지원서 제목")
  String title,

  @NotNull
  @Schema(description = "양식 확정 여부")
  boolean confirmYN,

  @Schema(description = "주관식 문항 생성 요청 list")
  List<@Valid UpdateTextQuestionRequest> updateTextQuestionList,

  @Schema(description = "객관식 문항 생성 요청 list")
  List<@Valid UpdateSelectQuestionRequest> updateSelectQuestionList
) {

  @Builder
  public UpdateApplicationRequest {
    if (updateTextQuestionList == null) {
      updateTextQuestionList = new ArrayList<>();
    }
    if (updateSelectQuestionList == null) {
      updateSelectQuestionList = new ArrayList<>();
    }
  }
}
