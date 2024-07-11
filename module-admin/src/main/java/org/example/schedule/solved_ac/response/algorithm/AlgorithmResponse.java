package org.example.schedule.solved_ac.response.algorithm;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Schema(description = "백준 알고리즘 API 응답 객체")
public class AlgorithmResponse {

  @Schema(description = "문제 목록")
  private int count;

  @Schema(description = "알고리즘 목록")
  @JsonProperty("items")
  @Default
  private List<AlgorithmDto> algorithmList = new ArrayList<>();
}
