package org.example.domain.answer.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;

@Schema(description = "작성한 지원서 목록 검색 요청 객체")
public record SearchAnswerRequest(

  @Min(1)
  @Schema(description = "페이지 번호", example = "1", requiredMode = RequiredMode.REQUIRED)
  int page,

  @Min(10)
  @Schema(description = "페이지별 개수", example = "10", requiredMode = RequiredMode.REQUIRED)
  int size
) {

  public PageRequest pageRequest() {
    return PageRequest.of(page - 1, size);
  }

}
