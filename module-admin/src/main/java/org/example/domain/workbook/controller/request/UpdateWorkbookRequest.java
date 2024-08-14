package org.example.domain.workbook.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문제집 수정 요청 객체")
public record UpdateWorkbookRequest(

  @Schema(description = "문제집 이름")
  String name

) {

}
