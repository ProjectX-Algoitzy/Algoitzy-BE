package org.example.domain.workbook.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ListProblemRepository;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.example.domain.week.Week;
import org.example.domain.week.repository.DetailWeekRepository;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.example.domain.workbook_problem.WorkbookProblem;
import org.example.util.ValueUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWorkbookService {

  private final WorkbookRepository workbookRepository;
  private final StudyRepository studyRepository;
  private final DetailWeekRepository detailWeekRepository;
  private final ListProblemRepository listProblemRepository;

  /**
   * 코딩테스트 대비반 문제집 생성
   */
  public void createCodingTestPrepareWorkbook() {
    Optional<Week> optionalWeek = detailWeekRepository.getCurrentWeek();
    if (optionalWeek.isEmpty()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디 진행 기간이 아닙니다.");
    }
    Week currentWeek = optionalWeek.get();

    Optional<Study> optionalStudy = studyRepository.findByNameAndTypeIs(ValueUtils.CODING_TEST_PREPARE, StudyType.REGULAR);
    if (optionalStudy.isEmpty()) {
      throw new GeneralException(ErrorStatus.NOT_FOUND, "코딩테스트 대비반이 존재하지 않습니다.");
    }
    Study codingTestPrepareStudy = optionalStudy.get();

    // 주차별 알고리즘 유형 지정 후 백준 문제 조회
    List<String> algorithmList = new ArrayList<>();
    switch (optionalWeek.get().getValue()) {
      case 1 -> algorithmList = List.of("bruteforcing", "backtracking");
      case 2 -> algorithmList = List.of("dp");
      case 3 -> algorithmList = List.of("simulation", "two_pointer");
      case 4 -> algorithmList = List.of("binary_search", "prefix_sum");
      case 5 -> algorithmList = List.of("data_structures");
      case 6 -> algorithmList = List.of("bfs", "dfs");
      case 7 -> algorithmList = List.of("dijkstra");
      case 8 -> algorithmList = List.of("greedy");
    }
    List<Problem> problemList = listProblemRepository.getProblemList(algorithmList);

    // 문제집 생성 및 저장
    Workbook workbook = Workbook.builder()
      .study(codingTestPrepareStudy)
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
