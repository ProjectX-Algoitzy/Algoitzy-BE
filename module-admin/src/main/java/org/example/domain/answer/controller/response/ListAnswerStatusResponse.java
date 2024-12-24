package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 전형 단계 목록 응답 객체")
public class ListAnswerStatusResponse {

  @Schema(description = "전형 단계 목록")
  private List<ListAnswerStatusDto> statusList;
}
