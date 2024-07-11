package org.example.schedule.solved_ac.response.problem;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.domain.problem.Level;
import org.example.domain.problem.Problem;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "백준 문제 API 응답 dto")
public class ProblemDto {

  @Schema(description = "백준 문제 번호")
  @JsonProperty("problemId")
  private int number;

  @Schema(description = "백준 문제 이름")
  @JsonProperty("titleKo")
  private String name;

  @Schema(description = "백준 문제 난이도")
  private int level;

  @Schema(description = "지원 언어 목록")
  @JsonProperty("titles")
  private List<LanguageDto> languageList;

  @Schema(description = "연관 알고리즘 목록")
  @JsonProperty("tags")
  private List<AlgorithmDto> algorithmList;

  public Problem toEntity() {
    List<String> languageList = this.getLanguageList().stream()
      .map(LanguageDto::getLanguage)
      .toList();

    return Problem.builder()
      .number(this.getNumber())
      .name(this.getName())
      .level(Level.findByLevel(this.getLevel()))
      .languageList(languageList)
      .build();
  }
}
