package org.example.domain.institution.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.domain.institution.enums.InstitutionType;

@Schema(description = "기관 생성 요청 객체")
public record CreateInstitutionRequest(

  @NotBlank
  @Schema(description = "기관명", requiredMode = RequiredMode.REQUIRED)
  String name,

  @NotNull
  @Schema(description = "기관 유형", requiredMode = RequiredMode.REQUIRED)
  InstitutionType type,

  @NotBlank
  @Schema(description = "분석 내용", requiredMode = RequiredMode.REQUIRED)
  String content
) {

}
