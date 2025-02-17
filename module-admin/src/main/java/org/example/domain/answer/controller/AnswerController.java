package org.example.domain.answer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerStatusResponse;
import org.example.domain.answer.service.AnswerService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
@Tag(name = "AnswerController", description = "[ADMIN] 지원서 작성 관련 API")
public class AnswerController {

  private final AnswerService answerService;

  @GetMapping("/status")
  @Operation(summary = "지원서 전형 단계 목록 조회")
  public ApiResponse<ListAnswerStatusResponse> getAnswerStatusList() {
    return ApiResponse.onSuccess(answerService.getAnswerStatusList());
  }

  @GetMapping()
  @Operation(summary = "작성한 지원서 목록 조회")
  public ApiResponse<ListAnswerResponse> getAnswerList(
    @ParameterObject @ModelAttribute @Valid SearchAnswerRequest request) {
    return ApiResponse.onSuccess(answerService.getAnswerList(request));
  }

  @GetMapping("/{answer-id}")
  @Operation(summary = "작성한 지원서 상세 조회")
  public ApiResponse<DetailAnswerResponse> getAnswer(@PathVariable("answer-id") Long answerId) {
    return ApiResponse.onSuccess(answerService.getAnswer(answerId));
  }
}
