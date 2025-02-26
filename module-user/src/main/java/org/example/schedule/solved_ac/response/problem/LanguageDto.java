package org.example.schedule.solved_ac.response.problem;

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
@Schema(description = "지원 언어 dto")
public class LanguageDto {

  @Schema(description = "지원 언어")
  private String language;
}
