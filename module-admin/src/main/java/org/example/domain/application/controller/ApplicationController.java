package org.example.domain.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.application.controller.request.SearchApplicationRequest;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.example.domain.application.response.DetailApplicationResponse;
import org.example.domain.application.service.ApplicationService;
import org.example.domain.application.service.CoreApplicationService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
@Tag(name = "ApplicationController", description = "[ADMIN] 지원서 관련 API")
public class ApplicationController {

  private final CoreApplicationService coreApplicationService;
  private final ApplicationService applicationService;

  @PostMapping()
  @Operation(summary = "지원서 생성")
  public ApiResponse<Void> createApplication(@RequestBody @Valid CreateApplicationRequest request) {
    applicationService.createApplication(request);
    return ApiResponse.onSuccess();
  }

  @GetMapping()
  @Operation(summary = "지원서 양식 목록 조회")
  public ApiResponse<ListApplicationResponse> getApplicationList(
    @ParameterObject @ModelAttribute @Valid SearchApplicationRequest request) {
    return ApiResponse.onSuccess(applicationService.getApplicationList(request));
  }

  @GetMapping("/{application-id}")
  @Operation(summary = "지원서 양식 상세 조회")
  public ApiResponse<DetailApplicationResponse> getApplication(
    @PathVariable("application-id") Long applicationId) {
    return ApiResponse.onSuccess(coreApplicationService.getApplication(applicationId));
  }
}
