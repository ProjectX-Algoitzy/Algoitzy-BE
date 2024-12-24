package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 전형 단계 목록 응답 DTO")
public class ListAnswerStatusDto {

  @Schema(description = "전형 단계 enum code")
  private String code;

  @Schema(description = "전형 단계명")
  private String name;
}
