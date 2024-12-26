package org.example.domain.text_answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Schema(description = "작성한 주관식 문항 상세 응답 객체")
public class DetailTextAnswerDto {

  @Schema(description = "주관식 문항 ID")
  private long textQuestionId;

  @Schema(description = "문항 내용")
  private String question;

  @Schema(description = "필수 여부")
  private boolean isRequired;

  @Setter
  @Schema(description = "답변")
  private String text;

  @Schema(description = "문항 번호")
  private int sequence;
}
