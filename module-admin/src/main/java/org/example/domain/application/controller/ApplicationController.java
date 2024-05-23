package org.example.domain.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.UpdateApplicationRequest;
import org.example.domain.application.controller.response.CreateApplicationResponse;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.example.domain.application.response.DetailApplicationResponse;
import org.example.domain.application.service.ApplicationService;
import org.example.domain.application.service.CoreApplicationService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public ApiResponse<CreateApplicationResponse> createApplication() {
    return ApiResponse.onCreate(applicationService.createApplication());
  }

  @PatchMapping("/{application-id}")
  @Operation(summary = "지원서 임시저장")
  public ApiResponse<Void> updateApplication(
    @PathVariable("application-id") Long applicationId,
    @RequestBody @Valid UpdateApplicationRequest request) {
    applicationService.updateApplication(applicationId, request);
    return ApiResponse.onSuccess();
  }

  @GetMapping()
  @Operation(summary = "근 4기수 지원서 양식 목록 조회")
  public ApiResponse<ListApplicationResponse> getApplicationList() {
    return ApiResponse.onSuccess(applicationService.getApplicationList());
  }

  @GetMapping("/{application-id}")
  @Operation(summary = "지원서 양식 상세 조회")
  public ApiResponse<DetailApplicationResponse> getApplication(
    @PathVariable("application-id") Long applicationId) {
    return ApiResponse.onSuccess(coreApplicationService.getApplication(applicationId));
  }

  @PostMapping("/copy")
  @Operation(summary = "지원서 복사")
  public ApiResponse<Void> copyApplication(@Valid @RequestBody CopyApplicationRequest request) {
    applicationService.copyApplication(request);
    return ApiResponse.onSuccess();
  }

  @DeleteMapping("/{application-id}")
  @Operation(summary = "지원서 삭제")
  public ApiResponse<Void> copyApplication(
    @PathVariable("application-id") Long applicationId) {
    applicationService.deleteApplication(applicationId);
    return ApiResponse.onSuccess();
  }
}
