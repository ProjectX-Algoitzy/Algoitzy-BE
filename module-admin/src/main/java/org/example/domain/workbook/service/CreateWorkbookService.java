package org.example.domain.workbook.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem.service.CoreProblemService;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.study_member.repository.ListStudyMemberRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.controller.request.UpdateWorkbookRequest;
import org.example.domain.workbook.enums.CodingTestBasicWorkbook;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.example.domain.workbook_problem.WorkbookProblem;
import org.example.domain.workbook_problem.controller.request.CreateWorkbookProblemRequest;
import org.example.domain.workbook_problem.repository.WorkbookProblemRepository;
import org.example.email.enums.EmailType;
import org.example.email.service.CoreEmailService;
import org.example.schedule.solved_ac.SolvedAcClient;
import org.example.schedule.solved_ac.response.problem.ProblemDto;
import org.example.schedule.solved_ac.response.problem.ProblemResponse;
import org.example.util.ValueUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWorkbookService {

  private final CoreWorkbookService coreWorkbookService;
  private final CoreProblemService coreProblemService;
  private final CoreEmailService coreEmailService;
  private final ListStudyMemberRepository listStudyMemberRepository;
  private final DetailWeekRepository detailWeekRepository;

  private final WorkbookRepository workbookRepository;
  private final StudyRepository studyRepository;
  private final WorkbookProblemRepository workbookProblemRepository;
  private final ProblemRepository problemRepository;

  private final SolvedAcClient solvedAcClient;
  private static final String SORT = "solved";
  private static final String DIRECTION = "desc";

  @Value("${spring.mail.username}")
  private String koalaEmail;

  @Value("${spring.profiles.active}")
  private String profiles;

  /**
   * 문제집 자동 생성
   */
  public void createAutoWorkbook() {
    try {
      Optional<Week> optionalWeek = detailWeekRepository.getCurrentWeek();
      if (optionalWeek.isEmpty()) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디 진행 기간이 아닙니다.");
      }
      Week currentWeek = optionalWeek.get();

      List<Study> regularStudyList = studyRepository.findAllByType(StudyType.REGULAR);
      for (Study study : regularStudyList) {
        List<Problem> problemList;
        if (study.getName().equals(ValueUtils.CODING_TEST_BASIC)) problemList = createBasicWorkbook(currentWeek);
        else if (study.getName().equals(ValueUtils.CODING_TEST_PREPARE)) problemList = createPrepareWorkbook(currentWeek, study.getId());
        else continue;

        // 문제집 생성 및 저장
        Workbook workbook = Workbook.builder()
          .study(study)
          .week(currentWeek)
          .build();

        List<WorkbookProblem> workbookProblemList = problemList.stream()
          .map(problem ->
            WorkbookProblem.builder()
              .workbook(workbook)
              .problem(problem)
              .build()
          ).toList();
        workbook.setWorkbookProblemList(workbookProblemList); // 양방향
        workbookRepository.save(workbook);
      }

      coreEmailService.send(koalaEmail, EmailType.WORKBOOK_SCHEDULER.toString(), "[" + profiles + "] 문제집 자동생성 성공");
    } catch (Exception e) {
      coreEmailService.send(koalaEmail, EmailType.WORKBOOK_SCHEDULER.toString(), "[" + profiles + "] " + e.getMessage());
      throw new GeneralException(ErrorStatus.BAD_REQUEST, e.getMessage());
    }

  }

  /**
   * 코딩테스트 기초반
   */
  private List<Problem> createBasicWorkbook(Week week) {
    List<Integer> problemNumberList = CodingTestBasicWorkbook.findByWeek(week.getValue()).problemNumberList;
    return problemNumberList.stream()
      .map(coreProblemService::findByNumber)
      .toList();
  }

  /**
   * 코딩테스트 심화반
   */
  private List<Problem> createPrepareWorkbook(Week week, Long studyId) {
    // 난이도 설정 쿼리
    String silverQuery = "*s4..s1 ";
    String goldQuery = "*g ";

    // 주차별 알고리즘 유형 설정 쿼리
    StringBuilder query = new StringBuilder();
    switch (week.getValue()) {
      case 1 -> query.append("(#bruteforcing | #backtracking) -#dfs -#bfs");
      case 2 -> query.append("#dp");
      case 3 -> query.append("(#simulation | #two_pointer)");
      case 4 -> query.append("(#binary_search | #prefix_sum)");
      case 5 -> query.append("#data_structures");
      case 6 -> query.append("(#bfs | #dfs)");
      case 7 -> query.append("#dijkstra");
      case 8 -> query.append("#greedy");
    }

    // 스터디원이 푼 문제 제외 쿼리
    List<ListAttendanceDto> studyMemberList = listStudyMemberRepository.getStudyMemberList(studyId);
    studyMemberList.forEach(dto -> query.append(" -s@").append(dto.getHandle()));

    // solved.ac 요청
    ProblemResponse silverProblemResponse = solvedAcClient.searchProblems(1, silverQuery + query, SORT, DIRECTION);
    ProblemResponse goldProblemResponse = solvedAcClient.searchProblems(1, goldQuery + query, SORT, DIRECTION);

    List<Integer> problemNumberList = Stream.concat(
      silverProblemResponse.getProblemList().stream().limit(3),
      goldProblemResponse.getProblemList().stream().limit(3)
    ).map(ProblemDto::getNumber).toList();
    return problemRepository.findAllById(problemNumberList);
  }

  /**
   * 문제집 이름 수정
   */
  public void updateWorkbook(Long workbookId, UpdateWorkbookRequest request) {
    Workbook workbook = coreWorkbookService.findById(workbookId);
    workbook.update(request.name());
  }

  /**
   * 문제집 삭제
   */
  public void deleteWorkbook(Long workbookId) {
    workbookRepository.deleteById(workbookId);
  }

  /**
   * 문제집 문제 추가
   */
  public void createWorkbookProblem(Long workbookId, CreateWorkbookProblemRequest request) {
    Workbook workbook = coreWorkbookService.findById(workbookId);
    Problem problem = coreProblemService.findByNumber(request.number());
    if (workbookProblemRepository.findByWorkbookAndProblem(workbook, problem).isPresent()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "이미 문제집에 포함된 문제입니다.");
    }
    workbookProblemRepository.save(
      WorkbookProblem.builder()
        .workbook(workbook)
        .problem(problem)
        .build()
    );
  }

  /**
   * 문제집 문제 삭제
   */
  public void deleteWorkbookProblem(Long workbookId, Integer problemNumber) {
    workbookProblemRepository.deleteByWorkbookIdAndProblemNumber(workbookId, problemNumber);
  }
}
