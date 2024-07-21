package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkbookService {

  private final CreateWorkbookService createWorkbookService;

  public void createCodingTestPrepareWorkbook() {
    createWorkbookService.createCodingTestPrepareWorkbook();
  }
}
