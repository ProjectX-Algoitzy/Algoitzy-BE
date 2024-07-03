package org.example.domain.select_answer_field.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "객관식 문항 필드 상세 응답 객체")
public class DetailSelectAnswerFieldDto {

  @Schema(description = "필드 내용")
  private String context;

  @Schema(description = "선택 여부")
  private boolean isSelected;
}
