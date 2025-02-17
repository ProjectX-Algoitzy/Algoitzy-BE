package org.example.domain.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.application.controller.response.DetailApplicationResponse;
import org.example.domain.application.service.ApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name = "ApplicationController", description = "[USER] 지원서 관련 API")
public class ApplicationController {

  private final ApplicationService applicationService;

  @GetMapping("/{application-id}")
  @Operation(summary = "지원서 양식 상세 조회")
  public ApiResponse<DetailApplicationResponse> getApplication(
    @PathVariable("application-id") Long applicationId) {
    return ApiResponse.onSuccess(applicationService.getApplication(applicationId));
  }
}
