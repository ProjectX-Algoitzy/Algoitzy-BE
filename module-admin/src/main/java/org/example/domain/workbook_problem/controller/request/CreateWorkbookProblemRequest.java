package org.example.domain.workbook_problem.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(description = "문제집 문제 추가 요청 객체")
public record CreateWorkbookProblemRequest(

  @NotNull
  @Min(1000)
  Integer number
) {

}
