package org.example.domain.application.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "지원서 생성 결과 응답 객체")
public class CreateApplicationResponse {

  @Schema(description = "지원서 ID")
  private Long applicationId;

}
