package org.example.domain.field.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateFieldRequest(

  @NotBlank
  @Schema(description = "필드 내용", examples = {"소프트웨어학과", "5월 25일"})
  String context
) {

}
