package org.example.domain.workbook.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.workbook.Workbook;
import org.example.domain.workbook.repository.WorkbookRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreWorkbookService {

  private final WorkbookRepository workbookRepository;

  public Workbook findById(Long workbookId) {
    return workbookRepository.findById(workbookId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 문제집 ID입니다."));
  }
}
