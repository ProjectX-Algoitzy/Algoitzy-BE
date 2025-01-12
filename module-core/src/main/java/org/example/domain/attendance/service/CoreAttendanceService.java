package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreAttendanceService {

  private final AttendanceRepository attendanceRepository;

  public Attendance findById(Long id) {
    return attendanceRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 출석부 ID입니다."));
  }
}
