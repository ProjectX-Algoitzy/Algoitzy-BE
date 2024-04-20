package org.example.domain.answer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.answer.controller.request.CreateAnswerRequest;
import org.example.domain.answer.service.AnswerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
@Tag(name = "AnswerController", description = "[USER] 지원서 작성 관련 API")
public class AnswerController {

  private final AnswerService answerService;

  @PostMapping("/{application-id}")
  @Operation(summary = "지원서 작성")
  public ApiResponse<Void> createAnswer(
    @PathVariable("application-id") Long applicationId,
    @RequestBody @Valid CreateAnswerRequest request) {
    answerService.createAnswer(applicationId, request);
    return ApiResponse.onSuccess();
  }
}
