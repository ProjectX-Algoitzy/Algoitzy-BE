package org.example.domain.generation.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기수 목록 응답 DTO")
public class ListGenerationDto {

  @Schema(description = "기수 ID")
  private long generationId;

  @Schema(description = "기수")
  private int generation;

}
