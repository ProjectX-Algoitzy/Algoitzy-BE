package org.example.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.application.controller.request.CreateApplicationRequest;
import org.example.application.controller.response.CreateApplicationResponse;
import org.example.application.service.ApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name = "ApplicationController", description = "지원서 관련 API")
public class ApplicationController {

  private final ApplicationService applicationService;

  @PostMapping()
  @Operation(summary = "지원서 생성")
  public ApiResponse<CreateApplicationResponse> createApplication(@RequestBody @Valid CreateApplicationRequest request) {
    return ApiResponse.onSuccess(applicationService.createApplication(request));
  }
}
