package org.example.domain.workbook_problem.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.problem.Level;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "문제집 문제 목록 응답 DTO")
public class ListWorkbookProblemDto {

  @Schema(description = "백준 문제 번호")
  private int number;

  @Schema(description = "백준 문제 이름")
  private String name;

  @Schema(description = "문제 레벨 이미지 URL")
  private String levelUrl;

  public void setLevelUrl(Level level) {
    this.levelUrl = level.getImageUrl();
  }
}
