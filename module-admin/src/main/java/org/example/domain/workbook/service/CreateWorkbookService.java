package org.example.domain.workbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ListProblemRepository;
import org.example.domain.problem.service.CoreProblemService;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.controller.request.UpdateWorkbookRequest;
import org.example.domain.workbook.enums.CodingTestBasicWorkbook;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.example.domain.workbook_problem.WorkbookProblem;
import org.example.domain.workbook_problem.controller.request.CreateWorkbookProblemRequest;
import org.example.domain.workbook_problem.repository.WorkbookProblemRepository;
import org.example.util.ValueUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWorkbookService {

  private final CoreWorkbookService coreWorkbookService;
  private final CoreProblemService coreProblemService;
  private final WorkbookRepository workbookRepository;
  private final StudyRepository studyRepository;
  private final DetailWeekRepository detailWeekRepository;
  private final ListProblemRepository listProblemRepository;
  private final WorkbookProblemRepository workbookProblemRepository;

  /**
   * 문제집 자동 생성
   */
  public void createAutoWorkbook() {
    Optional<Week> optionalWeek = detailWeekRepository.getCurrentWeek();
    if (optionalWeek.isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디 진행 기간이 아닙니다.");
    }
    Week currentWeek = optionalWeek.get();

    List<Study> regularStudyList = studyRepository.findAllByType(StudyType.REGULAR);
    for (Study study : regularStudyList) {
      List<Problem> problemList;
      if (study.getName().equals(ValueUtils.CODING_TEST_BASIC)) problemList = createBasicWorkbook(currentWeek);
      else if (study.getName().equals(ValueUtils.CODING_TEST_PREPARE)) problemList = createPrepareWorkbook(currentWeek);
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
   * 코딩테스트 대비반
   */
  private List<Problem> createPrepareWorkbook(Week week) {
    // 주차별 알고리즘 유형 지정 후 백준 문제 조회
    List<String> algorithmList = new ArrayList<>();
    switch (week.getValue()) {
      case 1 -> algorithmList = List.of("bruteforcing", "backtracking");
      case 2 -> algorithmList = List.of("dp");
      case 3 -> algorithmList = List.of("simulation", "two_pointer");
      case 4 -> algorithmList = List.of("binary_search", "prefix_sum");
      case 5 -> algorithmList = List.of("data_structures");
      case 6 -> algorithmList = List.of("bfs", "dfs");
      case 7 -> algorithmList = List.of("dijkstra");
      case 8 -> algorithmList = List.of("greedy");
    }
    return listProblemRepository.getProblemList(algorithmList);
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
