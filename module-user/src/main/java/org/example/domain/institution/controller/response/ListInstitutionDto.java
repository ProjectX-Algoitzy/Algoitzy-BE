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
@Schema(description = "기관 목록 응답 DTO")
public class ListInstitutionDto {

  @Schema(description = "기관 ID")
  private long institutionId;

  @Schema(description = "기관명")
  private String name;

  @Schema(description = "조회수")
  private long viewCount;
}
