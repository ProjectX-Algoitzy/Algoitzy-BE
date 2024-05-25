package org.example.domain.interview.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.interview.controller.request.UpdateInterviewRequest;
import org.example.domain.interview.service.InterviewService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
@Tag(name = "InterviewController", description = "[ADMIN] 면접 관련 API")
public class InterviewController {

  private final InterviewService interviewService;

  @PatchMapping()
  @Operation(summary = "면접 일정 수정")
  public ApiResponse<Void> updateInterview(@RequestBody @Valid UpdateInterviewRequest request) {
    interviewService.updateInterview(request);
    return ApiResponse.onSuccess();
  }
}
