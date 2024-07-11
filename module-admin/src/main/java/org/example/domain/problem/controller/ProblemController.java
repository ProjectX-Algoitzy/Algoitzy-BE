package org.example.domain.problem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.problem.service.ProblemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Tag(name = "ProblemController", description = "[ADMIN] 백준 문제 크롤링 관련 API")
public class ProblemController {

  private final ProblemService problemService;

  @PostMapping()
  @Operation(summary = "백준 문제 저장")
  public ApiResponse<Void> createProblem() {
    problemService.createProblem();
    return ApiResponse.onCreate();
  }

}
