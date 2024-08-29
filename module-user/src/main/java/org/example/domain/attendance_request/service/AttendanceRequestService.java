package org.example.domain.attendance_request.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.request.CreateAttendanceRequestRequest;
import org.example.domain.attendance_request.controller.response.DetailAttendanceRequestResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceRequestService {

  private final CreateAttendanceRequestService createAttendanceRequestService;
  private final DetailAttendanceRequestService detailAttendanceRequestService;

  /**
   * 출석 요청 생성
   */
  public void createAttendanceRequest(CreateAttendanceRequestRequest request) {
    createAttendanceRequestService.createAttendanceRequest(request);
  }

  /**
   * 현재 주차 출석 요청 조회
   */
  public DetailAttendanceRequestResponse getAttendanceRequest(Long studyId) {
    return detailAttendanceRequestService.getAttendanceRequest(studyId);
  }
}
