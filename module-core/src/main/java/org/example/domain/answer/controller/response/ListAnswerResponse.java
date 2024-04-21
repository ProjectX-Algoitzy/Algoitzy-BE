package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;

@Builder
public record ListAnswerResponse(

  @Schema(description = "작성한 지원서 목록 객체 리스트")
  List<ListAnswerDto> answerList,

  @Schema(description = "작성한 지원서 총 개수")
  long totalCount
) {

}
