package org.example.domain.institution.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.domain.institution.enums.InstitutionType;

@Schema(description = "기관 수정 요청 객체")
public record UpdateInstitutionRequest(

  @Schema(description = "기관명")
  String name,

  @Schema(description = "기관 유형")
  InstitutionType type,

  @Schema(description = "분석 내용")
  String content
) {


}
