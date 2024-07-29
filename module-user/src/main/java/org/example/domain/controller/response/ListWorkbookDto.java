package org.example.domain.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "문제집 목록 응답 DTO")
public class ListWorkbookDto {

  @Schema(description = "문제집 ID")
  private long workbookId;

  @Schema(description = "주차")
  private int week;

  @Default
  @Schema(description = "문제 목록")
  private List<ListWorkbookProblemDto> problemList = new ArrayList<>();

}
