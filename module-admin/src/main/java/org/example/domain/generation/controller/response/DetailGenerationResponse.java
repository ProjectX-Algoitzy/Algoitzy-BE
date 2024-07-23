package org.example.domain.generation.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.week.controller.response.ListWeekDto;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기수 정보 응답 객체")
public class DetailGenerationResponse {

  @Schema(description = "기수")
  private int generation;

  @Default
  private List<ListWeekDto> weekList = new ArrayList<>();
}
