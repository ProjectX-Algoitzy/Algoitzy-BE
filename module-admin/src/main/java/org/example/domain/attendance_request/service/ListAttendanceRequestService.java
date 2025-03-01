package org.example.domain.attendance_request.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.response.ListAttendanceRequestDto;
import org.example.domain.attendance_request.controller.response.ListAttendanceRequestResponse;
import org.example.domain.attendance_request.repository.ListAttendanceRequestRepository;
import org.example.domain.attendance_request_problem.repository.ListAttendanceRequestProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAttendanceRequestService {

  private final ListAttendanceRequestProblemRepository listAttendanceRequestProblemRepository;
  private final ListAttendanceRequestRepository listAttendanceRequestRepository;

  /**
   * 출석 요청 내역 목록 조회
   */
  public ListAttendanceRequestResponse getAttendanceRequestList(Long studyId, String handle) {
    List<ListAttendanceRequestDto> attendanceRequestList =
      listAttendanceRequestRepository.getAttendanceRequestList(studyId, handle);
    for (ListAttendanceRequestDto dto : attendanceRequestList) {
      List<String> problemList = listAttendanceRequestProblemRepository.getAttendanceRequestProblemList(
        dto.getAttendanceRequestId());
      dto.setProblemUrlList(problemList);
    }

    return ListAttendanceRequestResponse.builder()
      .attendanceRequestList(attendanceRequestList)
      .build();
  }
}
