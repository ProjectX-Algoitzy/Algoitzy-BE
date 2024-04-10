package org.example.domain.field.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record CreateFieldRequest(

  @Schema(description = "문자형 필드", example = "소프트웨어학과")
  String stringField,

  @Schema(description = "날짜형 필드")
  LocalDate dateField
) {

}
