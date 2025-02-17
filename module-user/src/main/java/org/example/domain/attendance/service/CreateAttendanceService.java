package org.example.domain.attendance.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.repository.AttendanceRepository;
import org.example.domain.attendance_request.AttendanceRequest;
import org.example.domain.attendance_request.repository.AttendanceRequestRepository;
import org.example.domain.attendance_request_problem.repository.ListAttendanceRequestProblemRepository;
import org.example.domain.study.controller.response.ListRegularStudyDto;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.domain.workbook.enums.CodingTestBasicMockTest;
import org.example.domain.workbook.enums.CodingTestBasicWorkbook;
import org.example.domain.workbook_problem.repository.ListWorkbookProblemRepository;
import org.example.email.enums.EmailType;
import org.example.email.service.CoreEmailService;
import org.example.util.ValueUtils;
import org.example.util.http_request.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateAttendanceService {

  private final CoreEmailService coreEmailService;
  private final DetailWeekRepository detailWeekRepository;
  private final ListAttendanceRequestProblemRepository listAttendanceRequestProblemRepository;
  private final ListStudyRepository listStudyRepository;
  private final ListWorkbookProblemRepository listWorkbookProblemRepository;
  private final StudyMemberRepository studyMemberRepository;
  private final AttendanceRequestRepository attendanceRequestRepository;
  private final AttendanceRepository attendanceRepository;

  private final WebDriver webDriver;
  private final Actions actions;

  @Value("${spring.mail.username}")
  private String koalaEmail;

  @Value("${spring.profiles.active}")
  private String profiles;

  private static final int CODING_TEST_PREPARE_MIN_REQUEST_COUNT = 3; // 코딩테스트 심화반 최소 문제 인증 개수
  private static final int WORKBOOK_MIN_REQUEST_COUNT = 2; // 모의테스트 최소 문제 인증 개수
  private static final int ONE_YEAR = 365;

  public void createAttendance() {
    try { // 갱신 주차 조회
      Optional<Week> optionalWeek = detailWeekRepository.getLastWeek();
      if (optionalWeek.isEmpty()) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "출석부 갱신 시점이 아닙니다.");
      }
      Week lastWeek = optionalWeek.get();
      if (attendanceRepository.existsAllByWeek(lastWeek)) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, lastWeek.getValue() + "주차 출석부가 이미 갱신되었습니다.");
      }

      log.info("{}주차 출석부 갱신 시작", lastWeek.getValue());
      // 스터디 순회
      List<ListRegularStudyDto> regularStudyList = listStudyRepository.getRegularStudyList(true);
      for (ListRegularStudyDto study : regularStudyList) {
        List<StudyMember> studyMemberList = studyMemberRepository.findAllByStudyIdAndStatus(study.getStudyId(), StudyMemberStatus.PASS);

        // 스터디원 출석 처리
        List<Attendance> attendanceList = new ArrayList<>();
        for (StudyMember studyMember : studyMemberList) {
          // 출석 인증 정보 조회
          Optional<AttendanceRequest> optionalAttendanceRequest =
            attendanceRequestRepository.findByWeekAndStudyMember(lastWeek, studyMember);
          boolean attendanceRequestYn = optionalAttendanceRequest.isPresent();

          // 인증 회원
          int solvedWorkbookCount = getSolvedWorkbookCount(lastWeek, studyMember);
          int requestCount = 0;
          if (attendanceRequestYn)
            requestCount = listAttendanceRequestProblemRepository.getRequestCount(optionalAttendanceRequest.get());

          Attendance attendance = Attendance.builder()
            .studyMember(studyMember)
            .week(lastWeek)
            .problemYN(getProblemYN(lastWeek, studyMember, requestCount - solvedWorkbookCount))
            .blogYN(attendanceRequestYn && StringUtils.hasText(optionalAttendanceRequest.get().getBlogUrl()))
            .workbookYN(solvedWorkbookCount >= WORKBOOK_MIN_REQUEST_COUNT)
            .build();
          attendanceList.add(attendance);
          // 양방향
          studyMember.getAttendanceList().add(attendance);

        }
        attendanceRepository.saveAll(attendanceList);
      }

      coreEmailService.send(koalaEmail, EmailType.ATTENDANCE_SCHEDULER.toString(), "[" + profiles + "]" + " 출석부 자동갱신 성공");
    } catch (Exception e) {
      coreEmailService.send(koalaEmail, EmailType.ATTENDANCE_SCHEDULER.toString(), "[" + profiles + "] " + e.getMessage());
      throw new GeneralException(ErrorStatus.BAD_REQUEST, e.getMessage());
    }

  }

  /**
   * 백준 지난 주 해결한 문제 수 크롤링
   *
   * @param requestCount 백준 외 플랫폼 문제 풀이 수
   * @rect : 유저 페이지 달력 영역
   * @google-visualization-tooltip : 해결 일자, 해결 수 툴팁
   */
  private boolean getProblemYN(Week week, StudyMember studyMember, int requestCount) {
    // 코딩테스트 기초반
    if (studyMember.getStudy().getName().equals(ValueUtils.CODING_TEST_BASIC)) {
      return getBasicProblemYN(week, studyMember);
    }

    // 코딩테스트 심화반
    else if (studyMember.getStudy().getName().equals(ValueUtils.CODING_TEST_PREPARE)) {

      // 8주차는 문제 인증 X
      if (week.getValue().equals(8)) return true;

      // 페이지 랜딩 대기
      webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl(studyMember.getMember().getHandle()));
      new WebDriverWait(webDriver, Duration.ofSeconds(10))
        .until(ExpectedConditions.presenceOfElementLocated(By.tagName("rect")));
      List<WebElement> rectList = webDriver.findElements(By.tagName("rect"));

      // 지난주 범위 계산
      LocalDate today = LocalDate.now().minusDays(7);
      LocalDate lastYear = today.minusYears(1);
      int startRect = today.getDayOfYear()
        - LocalDate.now().get(ChronoField.DAY_OF_WEEK) + 1;
      if (rectList.size() >= 2 * ONE_YEAR) startRect += (int) ChronoUnit.DAYS.between(lastYear, today);
      int lastRect = startRect + 6;

      int count = 0;
      for (int i = startRect; i <= lastRect; i++) {
        rectList = webDriver.findElements(By.tagName("rect"));
        WebElement rect = rectList.get(i);
        actions.moveToElement(rect).perform();

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          log.error(e.getMessage());
        }
        // tooltip에 기록된 해당 날짜에 푼 문제 수 추출
        WebElement tooltip = webDriver.findElement(By.className("google-visualization-tooltip"));
        int colonIndex = tooltip.getText().indexOf(":");
        if (colonIndex != -1) {
          count += Integer.parseInt(tooltip.getText().substring(colonIndex + 1).strip());
        }
      }

      // 백준 풀이 수 + requestCount
      int solvedCount = count + requestCount;
      log.info("{} 문제 풀이 수 : {}", studyMember.getMember().getName(), solvedCount);
      return solvedCount >= CODING_TEST_PREPARE_MIN_REQUEST_COUNT;
    }

    return true;
  }

  /**
   * 기초반 문제집 풀이 여부 반환
   */
  private Boolean getBasicProblemYN(Week week, StudyMember studyMember) {
    List<Integer> problemNumberList = getSolvedProblemNumberList(studyMember);

    CodingTestBasicMockTest mockTest = CodingTestBasicMockTest.findByWeek(week.getValue());
    return new HashSet<>(problemNumberList).containsAll(mockTest.problemNumberList);
  }

  /**
   * 백준 해결한 문제 번호 크롤링
   */
  private int getSolvedWorkbookCount(Week week, StudyMember studyMember) {
    List<Integer> problemNumberList = getSolvedProblemNumberList(studyMember);

    int solvedCount = 0;
    switch (studyMember.getStudy().getName()) {
      case ValueUtils.CODING_TEST_PREPARE -> {
        List<Integer> workbookProblemList = listWorkbookProblemRepository.getWorkbookProblemList(week, studyMember.getStudy());
        solvedCount = (int) workbookProblemList.stream().filter(problemNumberList::contains).count();
      }
      case ValueUtils.CODING_TEST_BASIC -> {
        CodingTestBasicWorkbook workbook = CodingTestBasicWorkbook.findByWeek(week.getValue());
        solvedCount = (int) workbook.problemNumberList.stream().filter(problemNumberList::contains).count();
      }
    }

    log.info("{} 모의테스트 풀이 수 : {}", studyMember.getMember().getName(), solvedCount);
    return solvedCount;
  }

  /**
   * 백준 맞힌 문제 번호 리스트 반환
   *
   * @u-solved : 맞힌 문제 수 선택자
   * @.problem-list a : 문제 번호 선택자
   */
  private List<Integer> getSolvedProblemNumberList(StudyMember studyMember) {
    webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl(studyMember.getMember().getHandle()));

    // 맞힌 문제 개수로 제한
    int limitCount = Integer.parseInt(webDriver.findElement(By.id("u-solved")).getText());
    return
      webDriver.findElements(By.cssSelector(".problem-list a"))
        .stream()
        .map(problemNumber -> Integer.parseInt(problemNumber.getText()))
        .limit(limitCount)
        .toList();
  }

}
