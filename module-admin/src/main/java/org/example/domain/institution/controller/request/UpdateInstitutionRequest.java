package org.example.domain.institution.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import org.example.domain.institution.enums.InstitutionType;

@Schema(description = "기관 수정 요청 객체")
public record UpdateInstitutionRequest(

  @Schema(description = "기관명")
  String name,

  @Schema(description = "기관 유형")
  String type,

  @Schema(description = "분석 내용")
  String content
) {


  @AssertTrue(message = "존재하지 않는 기관 유형입니다.")
  @Schema(hidden = true)
  public boolean getInstitutionTypeValidate() {
    return InstitutionType.exist(type);
  }

}
