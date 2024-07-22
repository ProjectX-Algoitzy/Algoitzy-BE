package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final CreateAttendanceService createAttendanceService;
  private final ListAttendanceService listAttendanceService;

  public void createAttendance() {
    createAttendanceService.createAttendance();
  }

  /**
   * 정규 스터디 출석부 조회
   */
  public ListAttendanceResponse getAttendanceList(Long studyId) {
    return listAttendanceService.getAttendanceList(studyId);
  }
}
