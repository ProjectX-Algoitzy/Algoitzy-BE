package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.problem.Problem;
import org.example.domain.problem.service.CoreProblemService;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.controller.request.UpdateWorkbookRequest;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.example.domain.workbook_problem.WorkbookProblem;
import org.example.domain.workbook_problem.controller.request.CreateWorkbookProblemRequest;
import org.example.domain.workbook_problem.repository.WorkbookProblemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateWorkbookService {

  private final CoreWorkbookService coreWorkbookService;
  private final CoreProblemService coreProblemService;

  private final WorkbookRepository workbookRepository;
  private final WorkbookProblemRepository workbookProblemRepository;


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
