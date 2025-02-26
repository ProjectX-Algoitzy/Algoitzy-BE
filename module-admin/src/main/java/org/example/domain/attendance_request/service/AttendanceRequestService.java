package org.example.domain.attendance_request.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.response.ListAttendanceRequestResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceRequestService {

  private final ListAttendanceRequestService listAttendanceRequestService;

  /**
   * 출석 요청 내역 목록 조회
   */
  public ListAttendanceRequestResponse getAttendanceRequestList(Long studyId, String handle) {
    return listAttendanceRequestService.getAttendanceRequestList(studyId, handle);
  }
}
