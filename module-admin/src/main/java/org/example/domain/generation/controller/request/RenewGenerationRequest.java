package org.example.domain.generation.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.week.controller.request.WeekDto;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "기수 갱신 요청 객체")
public class RenewGenerationRequest {

  @Size(min = 8, max = 8)
  @Default
  private List<@Valid WeekDto> weekList = new ArrayList<>();
}
