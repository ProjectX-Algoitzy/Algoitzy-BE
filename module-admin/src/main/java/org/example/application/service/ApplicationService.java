package org.example.application.service;

import lombok.RequiredArgsConstructor;
import org.example.application.controller.request.CreateApplicationRequest;
import org.example.application.controller.response.CreateApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final CreateApplicationService createApplicationService;

  public CreateApplicationResponse createApplication(CreateApplicationRequest request) {
    return createApplicationService.createApplication(request);
  }
}
