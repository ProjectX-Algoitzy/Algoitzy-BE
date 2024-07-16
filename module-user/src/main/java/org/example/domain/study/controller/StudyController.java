package org.example.domain.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.attendance.service.AttendanceService;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.service.CurriculumService;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
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
  private final CurriculumService curriculumService;
  private final AttendanceService attendanceService;

  @GetMapping("/count")
  @Operation(summary = "최신 기수 스터디 개수")
  public ApiResponse<Integer> getStudyCount() {
    return ApiResponse.onSuccess(studyService.getStudyCount());
  }

  /************* 자율 스터디 *************/

  @PostMapping
  @Operation(summary = "자율 스터디 생성")
  public ApiResponse<Void> createTempStudy(
    @RequestBody @Valid CreateTempStudyRequest request) {
    studyService.createTempStudy(request);
    return ApiResponse.onCreate();
  }

  @GetMapping("/temp")
  @Operation(summary = "자율 스터디 목록 조회")
  public ApiResponse<ListStudyResponse> getTempStudyList() {
    return ApiResponse.onSuccess(studyService.getTempStudyList());
  }

  @GetMapping("/{study-id}")
  @Operation(summary = "자율 스터디 상세 조회")
  public ApiResponse<DetailTempStudyResponse> getTempStudy(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(studyService.getTempStudy(studyId));
  }

  /************* 정규 스터디 *************/

  @GetMapping("/regular")
  @Operation(summary = "정규 스터디 목록 조회")
  public ApiResponse<ListStudyResponse> getRegularStudyList() {
    return ApiResponse.onSuccess(studyService.getRegularStudyList());
  }

  @GetMapping("/{study-id}/info")
  @Operation(summary = "정규 스터디 정보 조회")
  public ApiResponse<RegularStudyInfoResponse> getRegularStudyInfo(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(studyService.getRegularStudyInfo(studyId));
  }

  @GetMapping("/{study-id}/home")
  @Operation(summary = "정규 스터디 홈 조회")
  public ApiResponse<String> getContent(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(studyService.getContent(studyId));
  }

  @GetMapping("/{study-id}/curriculum")
  @Operation(summary = "정규 스터디 커리큘럼 목록 조회")
  public ApiResponse<ListCurriculumResponse> getCurriculumList(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(curriculumService.getCurriculumList(studyId));
  }

  @GetMapping("/{study-id}/attendance")
  @Operation(summary = "정규 스터디 출석부 조회")
  public ApiResponse<ListAttendanceResponse> getAttendanceList(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(attendanceService.getAttendanceList(studyId));
  }

  /************* 나의 스터디 *************/

  @GetMapping("/my")
  @Operation(summary = "나의 스터디 목록 조회")
  public ApiResponse<ListStudyResponse> getMyStudyList() {
    return ApiResponse.onSuccess(studyService.getMyStudyList());
  }
}
