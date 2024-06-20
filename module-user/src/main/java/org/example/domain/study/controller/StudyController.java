package org.example.domain.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListTempStudyResponse;
import org.example.domain.study.service.StudyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(name = "StudyController", description = "[USER] 스터디 관련 API")
public class StudyController {

  private final StudyService studyService;

  @GetMapping("/count")
  @Operation(summary = "최신 기수 스터디 개수")
  public ApiResponse<Integer> getStudyCount() {
    return ApiResponse.onSuccess(studyService.getStudyCount());
  }

  @GetMapping("/max-generation")
  @Operation(summary = "스터디 최신 기수")
  public ApiResponse<Integer> getMaxStudyGeneration() {
    return ApiResponse.onSuccess(studyService.getMaxStudyGeneration());
  }

  @PostMapping
  @Operation(summary = "자율 스터디 생성")
  public ApiResponse<Void> createTempStudy(
    @RequestBody @Valid CreateTempStudyRequest request) {
    studyService.createTempStudy(request);
    return ApiResponse.onCreate();
  }

  @GetMapping
  @Operation(summary = "자율 스터디 목록 조회")
  public ApiResponse<ListTempStudyResponse> getTempStudyList() {
    return ApiResponse.onSuccess(studyService.getTempStudyList());
  }

  @GetMapping("/{study-id}")
  @Operation(summary = "자율 스터디 상세 조회")
  public ApiResponse<DetailTempStudyResponse> getTempStudy(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(studyService.getTempStudy(studyId));
  }

}
