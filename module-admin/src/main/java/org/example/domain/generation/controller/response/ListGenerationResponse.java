package org.example.domain.generation.controller.response;

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
@Schema(description = "기수 목록 응답 객체")
public class ListGenerationResponse {

  @Default
  @Schema(description = "기수 ID")
  private List<ListGenerationDto> generationList = new ArrayList<>();

}
