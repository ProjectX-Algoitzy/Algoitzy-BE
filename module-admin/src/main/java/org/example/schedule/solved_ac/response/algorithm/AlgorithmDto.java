package org.example.schedule.solved_ac.response.algorithm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.algorithm.Algorithm;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "알고리즘 dto")
public class AlgorithmDto {

  @Schema(description = "알고리즘 영어명")
  @JsonProperty("key")
  private String name;

  @Schema(description = "국가별 알고리즘명")
  @JsonProperty("displayNames")
  private List<AlgorithmNameDto> algorithmNameList = new ArrayList<>();

  public Algorithm toEntity() {
    AlgorithmNameDto koreanNameDto = this.algorithmNameList.stream()
      .filter(algorithmName -> algorithmName.getLanguage().equals("ko"))
      .toList().get(0);

    return Algorithm.builder()
      .englishName(this.name)
      .koreanName(koreanNameDto.getName())
      .build();
  }
}
