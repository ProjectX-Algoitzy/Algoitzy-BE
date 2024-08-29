package org.example.domain.generation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.generation.controller.request.RenewGenerationRequest;
import org.example.domain.generation.controller.response.DetailGenerationResponse;
import org.example.domain.generation.controller.response.ListGenerationResponse;
import org.example.domain.generation.service.GenerationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generation")
@RequiredArgsConstructor
@Tag(name = "GenerationController", description = "[ADMIN] 기수 관련 API")
public class GenerationController {

  private final GenerationService generationService;

  @PostMapping()
  @Operation(summary = "🚫기수 갱신🚫")
  public ApiResponse<Void> renewGeneration(@RequestBody @Valid RenewGenerationRequest request) {
    generationService.renewGeneration(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/current")
  @Operation(summary = "현재 기수 상세 조회")
  public ApiResponse<DetailGenerationResponse> getGeneration() {
    return ApiResponse.onSuccess(generationService.getGeneration());
  }

  @GetMapping
  @Operation(summary = "기수 목록 조회")
  public ApiResponse<ListGenerationResponse> getGenerationList() {
    return ApiResponse.onSuccess(generationService.getGenerationList());

  }
}
