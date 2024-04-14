package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final CreateApplicationService createApplicationService;

  /**
   * 지원서 작성
   */
  public void createApplication(CreateApplicationRequest request) {
    createApplicationService.createApplication(request);
  }
}
