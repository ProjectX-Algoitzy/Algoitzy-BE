package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.workbook.controller.response.ListWorkbookResponse;
import org.example.domain.workbook.service.ListWorkbookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttendanceService {

  private final CreateAttendanceService createAttendanceService;
  private final ListAttendanceService listAttendanceService;
  private final ListWorkbookService listWorkbookService;

  public void createAttendance() {
    createAttendanceService.createAttendance();
  }

  /**
   * 정규 스터디 출석부 조회
   */
  public ListAttendanceResponse getAttendanceList(Long studyId) {
    return listAttendanceService.getAttendanceList(studyId);
  }

  /**
   * 정규 스터디 모의테스트 조회
   */
  public ListWorkbookResponse getWorkbookList(Long studyId) {
    return listWorkbookService.getWorkbookList(studyId);
  }
}
