package org.example.schedule.solved_ac.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(description = "연관 알고리즘 dto")
public class AlgorithmDto {

  @Schema(description = "알고리즘 유형 이름")
  @JsonProperty("key")
  private String name;
}
