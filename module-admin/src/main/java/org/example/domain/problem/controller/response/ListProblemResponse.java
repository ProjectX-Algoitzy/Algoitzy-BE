package org.example.domain.problem.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "백준 문제 목록 조회 응답 객체")
public class ListProblemResponse {

  @Default
  @Schema(description = "백준 문제 목록")
  private List<ListProblemDto> problemList = new ArrayList<>();

  @Schema(description = "총 문제 수")
  private long totalCount;
}
