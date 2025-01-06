package org.example.domain.workbook.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.problem.Level;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;
import org.example.domain.workbook_problem.repository.ListWorkbookProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailWorkbookService {

  private final CoreWorkbookService coreWorkbookService;
  private final ListWorkbookProblemRepository listWorkbookProblemRepository;

  /**
   * 문제집 상세 조회
   */
  public DetailWorkbookResponse getWorkbook(Long workbookId) {
    Workbook workbook = coreWorkbookService.findById(workbookId);
    List<ListWorkbookProblemDto> problemList = listWorkbookProblemRepository.getWorkbookProblemList(workbookId);
    for (ListWorkbookProblemDto problem : problemList) {
      problem.setLevelUrl(Level.valueOf(problem.getLevelUrl()));
    }

    return DetailWorkbookResponse.builder()
      .name(workbook.getName())
      .problemList(problemList)
      .build();
  }
}
