package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.application.controller.request.SearchApplicationRequest;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final CreateApplicationService createApplicationService;
  private final ListApplicationService listApplicationService;

  public void createApplication(CreateApplicationRequest request) {
    createApplicationService.createApplication(request);
  }

  public ListApplicationResponse getApplicationList(SearchApplicationRequest request) {
    return listApplicationService.getApplicationList(request);
  }

  public void copyApplication(CopyApplicationRequest request) {
    createApplicationService.copyApplication(request);
  }
}
