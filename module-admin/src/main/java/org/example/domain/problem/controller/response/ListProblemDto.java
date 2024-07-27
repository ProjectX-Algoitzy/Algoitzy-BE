package org.example.domain.problem.controller.response;

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
@Schema(description = "백준 문제 목록 조회 응답 객체")
public class ListProblemDto {

  @Schema(description = "문제 번호")
  private int number;

  @Schema(description = "문제 이름")
  private String name;

  @Schema(description = "문제 레벨 이미지 URL")
  private String levelUrl;

  @Schema(description = "백준 링크 URL")
  private String baekjoonUrl;

  public void setLevelUrl(Level level) {
    this.levelUrl = level.getImageUrl();
  }
}
