package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final CreateAttendanceService createAttendanceService;

  public void createAttendance() {
    createAttendanceService.createAttendance();
  }
}
