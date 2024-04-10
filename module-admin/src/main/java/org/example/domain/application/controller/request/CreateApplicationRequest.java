package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.example.domain.select_question.controller.request.CreateSelectQuestionRequest;
import org.example.domain.text_question.controller.request.CreateTextQuestionRequest;

public record CreateApplicationRequest(

  @NotNull
  @Schema(description = "지원서 대상 스터디 ID", example = "1")
  Long studyId,

  @NotBlank
  @Schema(description = "지원서 제목", example = "지원서 제목")
  String title,

  @Schema(description = "주관식 문항 생성 요청 list")
  List<@Valid CreateTextQuestionRequest> createTextQuestionRequestList,

  @Schema(description = "객관식 문항 생성 요청 list")
  List<@Valid CreateSelectQuestionRequest> createSelectQuestionRequestList
) {

}
