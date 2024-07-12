package org.example.domain.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.controller.response.ListRegularStudyResponse;
import org.example.domain.study.service.StudyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Tag(name = "StudyController", description = "[ADMIN] 스터디 관련 API")
public class StudyController {

  private final StudyService studyService;

  @GetMapping()
  @Operation(summary = "정규 스터디 목록 조회")
  public ApiResponse<ListRegularStudyResponse> getRegularStudyList() {
    return ApiResponse.onSuccess(studyService.getRegularStudyList());
  }

  @PostMapping
  @Operation(summary = "정규 스터디 생성")
  public ApiResponse<Void> createRegularStudy(
    @RequestBody @Valid CreateRegularStudyRequest request) {
    studyService.createRegularStudy(request);
    return ApiResponse.onCreate();
  }
}
