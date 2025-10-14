package org.example.domain.challenge_problem.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.problem.Level;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챌린지 문제 상세 조회 응답 객체")
public class DetailChallengeProblemResponse {

  @Schema(description = "문제 번호")
  private Integer problemNumber;

  @Schema(description = "문제 난이도")
  private Level level;

  @Schema(description = "난이도 이미지")
  private String levelImageUrl;

  @Schema(description = "알고리즘 유형 목록")
  @Default
  private List<String> algorithmList = new ArrayList<>();
}
