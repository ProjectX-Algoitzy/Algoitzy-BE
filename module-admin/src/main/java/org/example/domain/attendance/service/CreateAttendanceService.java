package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.controller.request.UpdateAttendanceDto;
import org.example.domain.attendance.controller.request.UpdateAttendanceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateAttendanceService {

  private final CoreAttendanceService coreAttendanceService;

  /**
   * 정규 스터디 출석부 수정
   */
  public void updateAttendanceList(UpdateAttendanceRequest request) {
    for (UpdateAttendanceDto dto : request.attendanceList()) {
      Attendance attendance = coreAttendanceService.findById(dto.attendanceId());
      attendance.updateAttendance(dto.attendanceType());
    }
  }
}
