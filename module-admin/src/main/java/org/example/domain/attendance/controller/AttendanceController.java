package org.example.domain.attendance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.attendance.controller.request.UpdateAttendanceRequest;
import org.example.domain.attendance.service.AttendanceService;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
@Tag(name = "AttendanceController", description = "[ADMIN] 출석부 관련 API")
public class AttendanceController {

  private final AttendanceService attendanceService;

  @PatchMapping()
  @Operation(summary = "출석부 수정")
  public ApiResponse<Void> updateAttendanceList(
    @RequestBody @Valid UpdateAttendanceRequest request) {
    attendanceService.updateAttendanceList(request);
    return ApiResponse.onSuccess();
  }

}
