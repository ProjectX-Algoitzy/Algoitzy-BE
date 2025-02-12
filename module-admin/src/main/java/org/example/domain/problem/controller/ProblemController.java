package org.example.domain.problem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.problem.controller.request.SearchProblemRequest;
import org.example.domain.problem.controller.response.ListProblemResponse;
import org.example.domain.problem.service.ProblemService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
@Tag(name = "ProblemController", description = "[ADMIN] 문제 관련 API")
public class ProblemController {

  private final ProblemService problemService;

  @GetMapping()
  @Operation(summary = "백준 문제 목록 조회")
  public ApiResponse<ListProblemResponse> getProblemList(
    @ParameterObject @ModelAttribute @Valid SearchProblemRequest request
  ) {
    return ApiResponse.onSuccess(problemService.getProblemList(request));
  }

}
