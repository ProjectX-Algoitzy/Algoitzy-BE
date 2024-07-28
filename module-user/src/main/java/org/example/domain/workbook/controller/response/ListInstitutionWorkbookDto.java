package org.example.domain.workbook.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기관 문제집 목록 응답 DTO")
public class ListInstitutionWorkbookDto {

  @Schema(description = "문제집 ID")
  private long workbookId;

  @Schema(description = "이름")
  private String name;

}
