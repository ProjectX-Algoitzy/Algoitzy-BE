package org.example.domain.application.controller.response;

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
@Schema(description = "지원서 양식 목록 응답 객체")
public class ListApplicationResponse {

  @Default
  @Schema(description = "지원서 양식 list")
  private List<ListApplicationDto> applicationList = new ArrayList<>();
}
