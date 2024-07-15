package org.example.domain.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Tag(name = "AttendanceController", description = "[ADMIN] 출석부 관련 API")
public class AttendanceController {

  private final AttendanceService attendanceService;

  @PostMapping()
  @Operation(summary = "출석부 갱신")
  public ApiResponse<Void> createAttendance() {
    attendanceService.createAttendance();
    return ApiResponse.onCreate();
  }

}
