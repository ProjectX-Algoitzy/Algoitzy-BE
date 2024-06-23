package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.response.DetailApplicationResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ApplicationService {

  private final DetailApplicationService coreDetailApplicationService;

  /**
   * 지원서 양식 상세 조회
   */
  public DetailApplicationResponse getApplication(Long applicationId) {
    return coreDetailApplicationService.getApplication(applicationId);
  }
}
