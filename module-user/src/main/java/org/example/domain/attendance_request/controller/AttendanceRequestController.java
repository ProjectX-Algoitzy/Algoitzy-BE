package org.example.domain.attendance_request.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.attendance_request.controller.request.CreateAttendanceRequestRequest;
import org.example.domain.attendance_request.service.AttendanceRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance-request")
@RequiredArgsConstructor
@Tag(name = "AttendanceRequestController", description = "[USER] 출석 요청 관련 API")
public class AttendanceRequestController {

  private final AttendanceRequestService attendanceRequestService;

  @PostMapping
  @Operation(summary = "출석 요청 생성")
  public ApiResponse<Void> createAttendanceRequest(@RequestBody @Valid CreateAttendanceRequestRequest request) {
    attendanceRequestService.createAttendanceRequest(request);
    return ApiResponse.onCreate();
  }
}
