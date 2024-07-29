package org.example.domain.attendance.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance.controller.response.ListAttendanceResponse;
import org.example.domain.attendance.repository.ListAttendanceRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAttendanceService {

  private final CoreMemberService coreMemberService;
  private final CoreStudyService coreStudyService;
  private final ListAttendanceRepository listAttendanceRepository;
  private final StudyMemberRepository studyMemberRepository;

  public ListAttendanceResponse getAttendanceList(Long studyId) {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    Study study = coreStudyService.findById(studyId);
    if (studyMemberRepository.findByStudyAndMember(study, member).isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "스터디원만 열람할 수 있습니다.");
    }
    return ListAttendanceResponse.builder()
      .attendanceList(listAttendanceRepository.getAttendanceList(studyId))
      .build();
  }
}
