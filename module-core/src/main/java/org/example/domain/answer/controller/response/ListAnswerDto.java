package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "작성한 지원서 목록 응답 객체")
public class ListAnswerDto {

  @Schema(description = "답변 ID")
  private Long answerId;

  @Schema(description = "작성한 지원서 대상 스터디 이름")
  private String studyName;

  @Schema(description = "작성자 이름")
  private String submitName;

  @Schema(description = "제출일")
  private LocalDateTime submitTime;

}
