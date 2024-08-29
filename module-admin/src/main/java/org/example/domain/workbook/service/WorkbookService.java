package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.workbook.controller.request.UpdateWorkbookRequest;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.example.domain.workbook.controller.response.ListWorkbookResponse;
import org.example.domain.workbook_problem.controller.request.CreateWorkbookProblemRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkbookService {

  private final ListWorkbookService listWorkbookService;
  private final DetailWorkbookService detailWorkbookService;
  private final CreateWorkbookService createWorkbookService;

  public void createAutoWorkbook() {
    createWorkbookService.createAutoWorkbook();
  }

  /**
   * 정규 스터디 모의테스트 조회
   */
  public ListWorkbookResponse getWorkbookList(Long studyId) {
    return listWorkbookService.getWorkbookList(studyId);
  }

  /**
   * 문제집 이름 수정
   */
  public void updateWorkbook(Long workbookId, UpdateWorkbookRequest request) {
    createWorkbookService.updateWorkbook(workbookId, request);
  }

  /**
   * 문제집 삭제
   */
  public void deleteWorkbook(Long workbookId) {
    createWorkbookService.deleteWorkbook(workbookId);
  }

  /**
   * 문제집 상세 조회
   */
  public DetailWorkbookResponse getWorkbook(Long workbookId) {
    return detailWorkbookService.getWorkbook(workbookId);
  }

  /**
   * 문제집 문제 추가
   */
  public void createWorkbookProblem(Long workbookId, CreateWorkbookProblemRequest request) {
    createWorkbookService.createWorkbookProblem(workbookId, request);
  }

  /**
   * 문제집 문제 삭제
   */
  public void deleteWorkbookProblem(Long workbookId, Integer problemNumber) {
    createWorkbookService.deleteWorkbookProblem(workbookId, problemNumber);
  }
}
