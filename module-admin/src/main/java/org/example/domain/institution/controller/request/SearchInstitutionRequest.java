package org.example.domain.institution.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.domain.institution.enums.InstitutionSort;
import org.example.domain.institution.enums.InstitutionType;
import org.springframework.data.domain.PageRequest;

public record SearchInstitutionRequest(

  @Schema(description = "검색 키워드(기관명)")
  String searchKeyword,

  @NotNull
  @Schema(description = "기관 유형", allowableValues = {"COMPANY", "CAMP"}, requiredMode = RequiredMode.REQUIRED)
  InstitutionType type,

  @Schema(description = "기관 정렬 조건", allowableValues = {"NAME", "VIEW_COUNT"})
  InstitutionSort sort,

  @Min(1)
  @Schema(description = "페이지 번호", type = "integer", format = "int32", requiredMode = RequiredMode.REQUIRED)
  int page,

  @Min(10)
  @Schema(description = "페이지별 개수", type = "integer", format = "int32", requiredMode = RequiredMode.REQUIRED)
  int size
) {

  public PageRequest pageRequest() {
    return PageRequest.of(page - 1, size);
  }

}
