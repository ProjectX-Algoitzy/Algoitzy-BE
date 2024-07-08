package org.example.domain.attendance_request.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.request.CreateAttendanceRequestRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceRequestService {

  private final CreateAttendanceRequestService createAttendanceRequestService;

  /**
   * 출석 요청 생성
   */
  public void createAttendanceRequest(CreateAttendanceRequestRequest request) {
    createAttendanceRequestService.createAttendanceRequest(request);
  }
}
