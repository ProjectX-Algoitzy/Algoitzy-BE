package org.example.domain.attendance_request.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance_request.AttendanceRequest;
import org.example.domain.attendance_request.controller.request.CreateAttendanceRequestRequest;
import org.example.domain.attendance_request.repository.AttendanceRequestRepository;
import org.example.domain.attendance_request_problem.AttendanceRequestProblem;
import org.example.domain.attendance_request_problem.repository.AttendanceRequestProblemRepository;
import org.example.domain.attendance_request_problem.repository.DetailAttendanceRequestProblemRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.util.ArrayUtils;
import org.example.util.SecurityUtils;
import org.example.util.http_request.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateAttendanceRequestService {

  private final CoreStudyService coreStudyService;
  private final CoreMemberService coreMemberService;
  private final StudyMemberRepository studyMemberRepository;
  private final DetailWeekRepository detailWeekRepository;
  private final DetailAttendanceRequestProblemRepository detailAttendanceRequestProblemRepository;
  private final AttendanceRequestRepository attendanceRequestRepository;
  private final AttendanceRequestProblemRepository attendanceRequestProblemRepository;

  /**
   * 출석 요청 생성
   */
  public void createAttendanceRequest(CreateAttendanceRequestRequest request) {

    validate(request);

    // 현재 몇주차인지 탐색
    Optional<Week> optionalWeek = detailWeekRepository.getCurrentWeek();
    if (optionalWeek.isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디 진행 기간이 아닙니다.");
    }

    // 현재 스터디원 조회
    Week currentWeek = optionalWeek.get();
    Study study = coreStudyService.findById(request.studyId());
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    Optional<StudyMember> optionalStudyMember = studyMemberRepository.findByStudyAndMember(study, member);
    if (optionalStudyMember.isEmpty()) {
      throw new GeneralException(ErrorStatus.NOT_FOUND, study.getName() + "의 스터디원이 아닙니다.");
    }
    StudyMember studyMember = optionalStudyMember.get();

    // 기존 출석 요청 삭제
    Optional<AttendanceRequest> optionalAttendanceRequest = attendanceRequestRepository.findByWeekAndStudyMember(currentWeek, studyMember);
    optionalAttendanceRequest.ifPresent(attendanceRequestRepository::delete);

    // URL 유효성 검사
    validateUrl(request, studyMember);

    // 저장 로직
    AttendanceRequest attendanceRequest = attendanceRequestRepository.save(
      AttendanceRequest.builder()
        .studyMember(studyMember)
        .week(currentWeek)
        .blogUrl(request.blogUrl())
        .build()
    );

    List<AttendanceRequestProblem> attendanceRequestProblemList = request.problemUrlList().stream()
      .filter(StringUtils::hasText)
      .map(problemUrl ->
        AttendanceRequestProblem.builder()
          .attendanceRequest(attendanceRequest)
          .problemUrl(problemUrl)
          .build()
      ).toList();
    // 양방향
    attendanceRequest.setAttendanceRequestProblemList(attendanceRequestProblemList);
    attendanceRequestProblemRepository.saveAll(attendanceRequestProblemList);

  }

  private void validate(CreateAttendanceRequestRequest request) {
    if (!ObjectUtils.isEmpty(request.problemUrlList()) &&
      request.problemUrlList().stream()
        .filter(StringUtils::hasText)
        .anyMatch(problemUrl -> !problemUrl.contains("https://"))
    ) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "https://를 포함한 전체 문제 URL 경로를 입력해주세요.");
    }
    if (StringUtils.hasText(request.blogUrl()) && !request.blogUrl().contains("https://")) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "https://를 포함한 전체 블로그 URL 경로를 입력해주세요.");
    }
    if (!ArrayUtils.isUniqueArray(request.problemUrlList())) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "중복된 URL이 존재합니다.");
    }
  }

  private void validateUrl(CreateAttendanceRequestRequest request, StudyMember studyMember) {
    // 블로그 URL 유효성 검사
    if (attendanceRequestRepository.findByBlogUrl(request.blogUrl()).isPresent()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "해당 블로그 URL의 인증 이력이 존재합니다.");
    }
    if (!HttpRequest.getRequest(request.blogUrl(), String.class).getStatusCode().is2xxSuccessful()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "유효하지 않은 블로그 URL입니다.");
    }

    // 문제 URL 유효성 검사
    List<String> problemUrlList = request.problemUrlList();
    for (int i = 0; i < problemUrlList.size(); i++) {
      String problemUrl = problemUrlList.get(i);
      if (!StringUtils.hasText(problemUrl)) continue;

      if (!HttpRequest.getRequest(problemUrl, String.class).getStatusCode().is2xxSuccessful()) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, (i + 1) + "번째 문제 URL이 유효하지 않습니다.");
      }
      if (detailAttendanceRequestProblemRepository.findByProblemUrl(studyMember, problemUrl).isPresent())
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, (i + 1) + "번째 문제 URL의 인증 이력이 존재합니다.");
    }
  }
}

