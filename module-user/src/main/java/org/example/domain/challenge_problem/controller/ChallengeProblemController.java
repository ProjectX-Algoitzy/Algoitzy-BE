package org.example.domain.challenge_problem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge_problem.controller.response.DetailChallengeProblemResponse;
import org.example.domain.challenge_problem.service.ChallengeProblemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge/problem")
@RequiredArgsConstructor
@Tag(name = "ChallengeProblemController", description = "[USER] 챌린지 문제 관련 API")
public class ChallengeProblemController {

  private final ChallengeProblemService challengeProblemService;

  @GetMapping("/today")
  @Operation(summary = "금일 챌린지 문제 상세 조회")
  public ApiResponse<DetailChallengeProblemResponse> getChallengeProblem() {
    return ApiResponse.onSuccess(challengeProblemService.getChallengeProblem());
  }

}
