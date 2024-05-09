package org.example.domain.select_question.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.field.response.DetailFieldDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "지원서 양식 객관식 문항 응답 객체")
public class DetailSelectQuestionDto {

  @Schema(description = "객관식 문항 ID")
  private long selectQuestionId;

  @Schema(description = "문항 내용")
  private String question;

  @Schema(description = "필수 여부")
  private boolean isRequired;

  @Schema(description = "다중 선택 가능 여부")
  private boolean isMultiSelect;

  @Schema(description = "문항 번호")
  private int sequence;

  @Schema(description = "필드 list")
  private List<DetailFieldDto> fieldList;
}
