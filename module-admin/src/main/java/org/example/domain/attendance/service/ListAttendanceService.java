package org.example.domain.attendance.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.attendance.repository.ListAttendanceRepository;
import org.example.domain.study_member.repository.ListStudyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAttendanceService {

  private final ListAttendanceRepository listAttendanceRepository;
  private final ListStudyMemberRepository listStudyMemberRepository;

  /**
   * 정규 스터디 출석부 조회
   */
  public ListAttendanceResponse getAttendanceList(Long studyId) {
    List<ListAttendanceDto> attendanceList = listAttendanceRepository.getAttendanceList(studyId);
    if (ObjectUtils.isEmpty(attendanceList)) {
      attendanceList = listStudyMemberRepository.getStudyMemberList(studyId);
    }

    return ListAttendanceResponse.builder()
      .attendanceList(attendanceList)
      .build();
  }
}
