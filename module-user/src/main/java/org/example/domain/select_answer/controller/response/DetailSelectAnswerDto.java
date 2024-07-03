package org.example.domain.select_answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.select_answer_field.response.DetailSelectAnswerFieldDto;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "작성한 객관식 문항 상세 응답 객체")
public class DetailSelectAnswerDto {

  @Schema(description = "문항 내용")
  private String question;

  @Schema(description = "필수 여부")
  private boolean isRequired;

  @Schema(description = "다중 선택 가능 여부")
  private boolean isMultiSelect;

  @Default
  @Schema(description = "객관식 문항 필드 상세 응답 객체 list")
  private List<DetailSelectAnswerFieldDto> selectAnswerFieldList = new ArrayList<>();

}
