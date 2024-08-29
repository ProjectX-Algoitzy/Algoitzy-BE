package org.example.domain.attendance_request.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance_request.controller.response.DetailAttendanceRequestResponse;
import org.example.domain.attendance_request.repository.DetailAttendanceRequestRepository;
import org.example.domain.attendance_request_problem.repository.ListAttendanceRequestProblemRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailAttendanceRequestService {

  private final CoreStudyService coreStudyService;
  private final CoreMemberService coreMemberService;
  private final DetailAttendanceRequestRepository detailAttendanceRequestRepository;
  private final ListAttendanceRequestProblemRepository listAttendanceRequestProblemRepository;
  private final DetailWeekRepository detailWeekRepository;
  private final StudyMemberRepository studyMemberRepository;

  /**
   * 현재 주차 출석 요청 조회
   */
  public DetailAttendanceRequestResponse getAttendanceRequest(Long studyId) {
    // 현재 몇주차인지 탐색
    Optional<Week> optionalWeek = detailWeekRepository.getCurrentWeek();
    if (optionalWeek.isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디 진행 기간이 아닙니다.");
    }
    Week currentWeek = optionalWeek.get();

    // 현재 스터디원 조회
    Study study = coreStudyService.findById(studyId);
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    Optional<StudyMember> optionalStudyMember = studyMemberRepository.findByStudyAndMember(study, member);
    if (optionalStudyMember.isEmpty()) {
      throw new GeneralException(ErrorStatus.NOT_FOUND, study.getName() + "의 스터디원이 아닙니다.");
    }
    StudyMember studyMember = optionalStudyMember.get();

    DetailAttendanceRequestResponse attendanceRequest = detailAttendanceRequestRepository.getAttendanceRequest(currentWeek, studyMember);
    if (attendanceRequest.getAttendanceRequestId() != null) {
      attendanceRequest.setProblemUrlList(listAttendanceRequestProblemRepository.getAttendanceRequestProblemList(attendanceRequest.getAttendanceRequestId()));
    }
    return attendanceRequest;
  }
}
