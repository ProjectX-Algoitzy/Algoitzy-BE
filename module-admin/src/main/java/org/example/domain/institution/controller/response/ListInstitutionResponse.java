package org.example.domain.institution.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기관 목록 응답 객체")
public class ListInstitutionResponse {

  @Default
  @Schema(description = "기관 목록")
  private List<ListInstitutionDto> institutionList = new ArrayList<>();

  @Schema(description = "총 기관 수")
  private long totalCount;
}
