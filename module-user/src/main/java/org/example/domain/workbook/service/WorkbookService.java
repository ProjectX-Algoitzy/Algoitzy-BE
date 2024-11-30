package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.workbook.controller.response.ListWorkbookResponse;
import org.example.domain.workbook.controller.response.DetailWorkbookResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkbookService {

  private final ListWorkbookService listWorkbookService;
  private final DetailWorkbookService detailWorkbookService;

  /**
   * 정규 스터디 모의테스트 조회
   */
  public ListWorkbookResponse getWorkbookList(Long studyId) {
    return listWorkbookService.getWorkbookList(studyId);
  }

  /**
   * 문제집 상세 조회
   */
  public DetailWorkbookResponse getWorkbook(Long workbookId) {
    return detailWorkbookService.getWorkbook(workbookId);
  }
}
