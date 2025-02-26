package org.example.domain.algorithm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.algorithm.service.AlgorithmService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/algorithm")
@Tag(name = "AlgorithmController", description = "[ADMIN] 알고리즘 관련 API")
public class AlgorithmController {

  private final AlgorithmService algorithmService;

  @PostMapping()
  @Operation(summary = "백준 알고리즘 저장")
  public ApiResponse<Void> createAlgorithm() {
    algorithmService.createAlgorithm();
    return ApiResponse.onCreate();
  }
}
