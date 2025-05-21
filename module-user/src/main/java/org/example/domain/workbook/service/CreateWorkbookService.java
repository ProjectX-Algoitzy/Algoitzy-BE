package org.example.domain.workbook.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.example.domain.workbook.enums.CodingTestBasicWorkbook;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.example.domain.workbook_problem.WorkbookProblem;
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

  private final CoreProblemService coreProblemService;
  private final CoreEmailService coreEmailService;
  private final ListStudyMemberRepository listStudyMemberRepository;
  private final DetailWeekRepository detailWeekRepository;

  private final WorkbookRepository workbookRepository;
  private final StudyRepository studyRepository;
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
    List<ListAttendanceDto> studyMemberList = listStudyMemberRepository.getStudyMemberList(studyId);
    final int MAX_MEMBER_PER_QUERY = 10;
    int queryCount = (int) Math.ceil((double) studyMemberList.size() / MAX_MEMBER_PER_QUERY);

    // 난이도 설정 쿼리
    String silverQuery = "*s4..s1 ";
    String goldQuery = "*g ";

    // 주차별 알고리즘 유형 설정 쿼리
    String algorithmQuery = getAlgorithmQuery(week);

    // 스터디원이 푼 문제 제외 쿼리
    List<StringBuilder> queryList = new ArrayList<>();
    for (int count = 0; count < queryCount; count++) {
      queryList.add(new StringBuilder(algorithmQuery));
      for (int i = 0; i < MAX_MEMBER_PER_QUERY; i++) {
        int idx = count * MAX_MEMBER_PER_QUERY + i;
        if (idx >= studyMemberList.size()) break;

        queryList.get(count).append(" -s@").append(studyMemberList.get(idx));
      }
    }

    // solved.ac 요청
    List<Set<Integer>> silverProblemSetList = new ArrayList<>();
    List<Set<Integer>> goldProblemSetList = new ArrayList<>();
    for (int count = 0; count < queryCount; count++) {
      ProblemResponse silverProblemResponsePage1 = solvedAcClient.searchProblems(1, silverQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse silverProblemResponsePage2 = solvedAcClient.searchProblems(2, silverQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse goldProblemResponsePage1 = solvedAcClient.searchProblems(1, goldQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse goldProblemResponsePage2 = solvedAcClient.searchProblems(2, goldQuery + queryList.get(count), SORT, DIRECTION);

      Set<Integer> silverPage1 = silverProblemResponsePage1.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> silverPage2 = silverProblemResponsePage2.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> goldPage1 = goldProblemResponsePage1.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> goldPage2 = goldProblemResponsePage2.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));

      Set<Integer> silverProblemSet = new LinkedHashSet<>();
      silverProblemSet.addAll(silverPage1);
      silverProblemSet.addAll(silverPage2);
      silverProblemSetList.add(silverProblemSet);

      Set<Integer> goldProblemSet = new LinkedHashSet<>();
      goldProblemSet.addAll(goldPage1);
      goldProblemSet.addAll(goldPage2);
      goldProblemSetList.add(goldProblemSet);
    }

    List<Integer> result = new ArrayList<>();
    if (!silverProblemSetList.isEmpty()) {
      Set<Integer> silverResultSet = new LinkedHashSet<>(silverProblemSetList.get(0));
      for (int i = 1; i < silverProblemSetList.size(); i++) {
        silverResultSet.retainAll(silverProblemSetList.get(i));
      }
      result.addAll(silverResultSet.stream().limit(3).toList());
    }

    if (!goldProblemSetList.isEmpty()) {
      Set<Integer> goldResultSet = new LinkedHashSet<>(goldProblemSetList.get(0));
      for (int i = 1; i < goldProblemSetList.size(); i++) {
        goldResultSet.retainAll(goldProblemSetList.get(i));
      }
      result.addAll(goldResultSet.stream().limit(3).toList());
    }

    return problemRepository.findAllById(result);
  }

  private String getAlgorithmQuery(Week week) {
    String algorithmQuery = "";
    switch (week.getValue()) {
      case 1 -> algorithmQuery = "(#bruteforcing | #backtracking) -#dfs -#bfs";
      case 2 -> algorithmQuery = "#dp";
      case 3 -> algorithmQuery = "(#simulation | #two_pointer)";
      case 4 -> algorithmQuery = "(#binary_search | #prefix_sum)";
      case 5 -> algorithmQuery = "#data_structures";
      case 6 -> algorithmQuery = "(#bfs | #dfs)";
      case 7 -> algorithmQuery = "#dijkstra";
      case 8 -> algorithmQuery = "#greedy";
    }
    return algorithmQuery;
  }

}
