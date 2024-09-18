package org.example.domain.study.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.attendance.service.AttendanceService;
import org.example.domain.attendance_request.controller.response.DetailAttendanceRequestResponse;
import org.example.domain.attendance_request.service.AttendanceRequestService;
import org.example.domain.controller.response.ListWorkbookResponse;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.service.CurriculumService;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.controller.request.UpdateStudyRequest;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.ListStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
import org.example.domain.study.service.StudyService;
import org.example.domain.study_member.controller.response.ListTempStudyMemberResponse;
import org.example.domain.study_member.service.StudyMemberService;
import org.example.domain.workbook.service.WorkbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  private final AttendanceRequestService attendanceRequestService;
  private final WorkbookService workbookService;
  private final StudyMemberService studyMemberService;

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

  @PatchMapping("/{study-id}")
  @Operation(summary = "자율 스터디 수정")
  public ApiResponse<Void> updateStudy(
    @PathVariable("study-id") Long studyId,
    @RequestBody @Valid UpdateStudyRequest request) {
    studyService.updateStudy(studyId, request);
    return ApiResponse.onCreate();
  }

  @PatchMapping("/{study-id}/end")
  @Operation(summary = "자율 스터디 종료")
  public ApiResponse<Void> endTempStudy(@PathVariable("study-id") Long studyId) {
    studyService.endTempStudy(studyId);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/{study-id}/apply")
  @Operation(summary = "자율 스터디 지원")
  public ApiResponse<Void> applyTempStudy(
    @PathVariable("study-id") Long studyId) {
    studyService.applyTempStudy(studyId);
    return ApiResponse.onSuccess();
  }

  @PostMapping("/{study-member-id}/pass")
  @Operation(summary = "자율 스터디원 수락")
  public ApiResponse<Void> passTempStudy(
    @PathVariable("study-member-id") Long studyMemberId) {
    studyService.passTempStudy(studyMemberId);
    return ApiResponse.onSuccess();
  }

  @GetMapping("/{study-id}/study-member")
  @Operation(summary = "자율 스터디원 목록 조회")
  public ApiResponse<ListTempStudyMemberResponse> getTempStudyMemberList(
    @PathVariable("study-id") Long studyId) {
    return ApiResponse.onSuccess(studyMemberService.getTempStudyMemberList(studyId));
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
    @PathVariable("study-id") Long studyId) {
    return ApiResponse.onSuccess(attendanceService.getAttendanceList(studyId));
  }

  @GetMapping("/{study-id}/attendance-request")
  @Operation(summary = "현재 주차 출석 요청 조회")
  public ApiResponse<DetailAttendanceRequestResponse> getAttendanceRequest(
    @PathVariable("study-id") Long studyId) {
    return ApiResponse.onSuccess(attendanceRequestService.getAttendanceRequest(studyId));
  }

  @GetMapping("/{study-id}/workbook")
  @Operation(summary = "정규 스터디 모의테스트 조회")
  public ApiResponse<ListWorkbookResponse> getWorkbookList(
    @PathVariable("study-id") Long studyId
  ) {
    return ApiResponse.onSuccess(workbookService.getWorkbookList(studyId));
  }

  /************* 나의 스터디 *************/

  @GetMapping("/my")
  @Operation(summary = "나의 스터디 목록 조회")
  public ApiResponse<ListStudyResponse> getMyStudyList() {
    return ApiResponse.onSuccess(studyService.getMyStudyList());
  }
}
