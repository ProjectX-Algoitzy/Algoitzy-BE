package org.example.domain.workbook.controller.response;

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
@Schema(description = "문제집 상세 응답 객체")
public class DetailWorkbookResponse {

  @Schema(description = "문제집 이름")
  private String name;

  @Default
  @Schema(description = "문제 목록")
  private List<ListWorkbookProblemDto> problemList = new ArrayList<>();
}
