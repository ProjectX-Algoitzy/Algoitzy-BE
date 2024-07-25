package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.controller.response.ListWorkbookResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkbookService {

  private final ListWorkbookService listWorkbookService;

  /**
   * 정규 스터디 모의테스트 조회
   */
  public ListWorkbookResponse getWorkbookList(Long studyId) {
    return listWorkbookService.getWorkbookList(studyId);
  }
}
