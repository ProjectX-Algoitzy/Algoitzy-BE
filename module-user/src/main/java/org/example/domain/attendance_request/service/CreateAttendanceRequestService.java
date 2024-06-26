package org.example.domain.attendance_request.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.request.CreateAttendanceRequestRequest;
import org.example.domain.attendance_request.repository.AttendanceRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateAttendanceRequestService {

  private final AttendanceRequestRepository attendanceRequestRepository;

  /**
   * 출석 요청 생성
   */
  public void createAttendanceRequest(CreateAttendanceRequestRequest request) {

  }
}
