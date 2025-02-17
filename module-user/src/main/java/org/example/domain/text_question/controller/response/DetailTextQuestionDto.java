package org.example.domain.text_question.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 양식 주관식 문항 응답 객체")
public class DetailTextQuestionDto {

  @Schema(description = "주관식 문항 ID")
  private long textQuestionId;

  @Schema(description = "문항 내용")
  private String question;

  @Schema(description = "필수 여부")
  private boolean isRequired;

  @Schema(description = "문항 번호")
  private int sequence;
}
