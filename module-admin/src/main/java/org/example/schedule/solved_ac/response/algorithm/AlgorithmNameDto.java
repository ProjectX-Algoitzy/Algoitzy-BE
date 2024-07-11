package org.example.schedule.solved_ac.response.algorithm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "국가별 알고리즘명")
public class AlgorithmNameDto {

  @Schema(description = "국가 언어")
  private String language;
  @Schema(description = "알고리즘명")
  private String name;
}
