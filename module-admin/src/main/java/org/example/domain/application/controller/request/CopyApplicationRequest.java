package org.example.domain.application.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "지원서 양식 복사 요청 객체")
public record CopyApplicationRequest(

  @NotNull
  @Schema(description = "복사할 지원서 ID")
  Long applicationId
) {

}
