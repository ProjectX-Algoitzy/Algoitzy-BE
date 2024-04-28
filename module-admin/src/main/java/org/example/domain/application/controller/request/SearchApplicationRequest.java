package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SearchApplicationRequest(

  @NotNull
  @Schema(description = "스터디 기수", example = "14")
  int generation
) {

}
