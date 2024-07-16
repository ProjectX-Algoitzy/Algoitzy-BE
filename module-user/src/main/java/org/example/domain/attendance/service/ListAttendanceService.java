package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.attendance.repository.ListAttendanceRepository;
import org.example.domain.week.repository.DetailWeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAttendanceService {

  private final ListAttendanceRepository listAttendanceRepository;
  private final DetailWeekRepository detailWeekRepository;

  public ListAttendanceResponse getAttendanceList(Long studyId) {
    return ListAttendanceResponse.builder()
      .attendanceList(listAttendanceRepository.getAttendanceList(studyId))
      .build();
  }
}
