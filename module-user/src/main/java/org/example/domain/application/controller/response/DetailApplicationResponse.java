package org.example.domain.application.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.select_question.controller.response.DetailSelectQuestionDto;
import org.example.domain.text_question.controller.response.DetailTextQuestionDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 양식 상세 응답 객체")
public class DetailApplicationResponse {

  @Schema(description = "지원서 제목")
  private String title;

  @Schema(description = "대상 스터디 이름")
  private String studyName;

  @Schema(description = "객관식 문항 list")
  private List<DetailSelectQuestionDto> selectQuestionList;

  @Schema(description = "주관식 문항 list")
  private List<DetailTextQuestionDto> textQuestionList;

}
