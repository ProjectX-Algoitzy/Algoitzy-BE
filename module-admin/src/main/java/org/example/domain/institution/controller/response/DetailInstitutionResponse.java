package org.example.domain.institution.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기관 상세 응답 객체")
public class DetailInstitutionResponse {

  @Schema(description = "기관 ID")
  private long institutionId;

  @Schema(description = "기관명")
  private String name;

  @Schema(description = "분석 내용")
  private String content;
}
