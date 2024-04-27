package org.example.domain.text_answer.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "작성한 주관식 문항 상세 응답 객체")
public class DetailTextAnswerDto {

  @Schema(description = "문항 내용")
  private String question;

  @Schema(description = "답변")
  private String text;

}
