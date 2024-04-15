package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ValidateHandleRequest(

  @NotBlank
  @Schema(description = "백준 핸들", example = "engus525")
  String handle
) {
}
