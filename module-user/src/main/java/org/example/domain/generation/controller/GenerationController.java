package org.example.domain.generation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.generation.controller.response.ListGenerationResponse;
import org.example.domain.generation.service.GenerationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generation")
@RequiredArgsConstructor
@Tag(name = "GenerationController", description = "[USER] 기수 관련 API")
public class GenerationController {

  private final GenerationService generationService;

  @GetMapping("/max")
  @Operation(summary = "최신 기수")
  public ApiResponse<Integer> getMaxGeneration() {
    return ApiResponse.onSuccess(generationService.getMaxGeneration());
  }

  @GetMapping
  @Operation(summary = "기수 목록 조회")
  public ApiResponse<ListGenerationResponse> getGenerationList() {
    return ApiResponse.onSuccess(generationService.getGenerationList());

  }
}
