package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.UpdateApplicationRequest;
import org.example.domain.application.controller.response.CreateApplicationResponse;
import org.example.domain.application.controller.response.DetailApplicationResponse;
import org.example.domain.application.controller.response.ListApplicationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

  private final CreateApplicationService createApplicationService;
  private final ListApplicationService listApplicationService;
  private final DetailApplicationService detailApplicationService;

  public CreateApplicationResponse createApplication() {
    return createApplicationService.createApplication();
  }

  public void updateApplication(Long applicationId, UpdateApplicationRequest request) {
    createApplicationService.updateApplication(applicationId, request);
  }

  public ListApplicationResponse getApplicationList() {
    return listApplicationService.getApplicationList();
  }

  public DetailApplicationResponse getApplication(Long applicationId) {
    return detailApplicationService.getApplication(applicationId);
  }

  public void copyApplication(CopyApplicationRequest request) {
    createApplicationService.copyApplication(request);
  }

  public void deleteApplication(Long applicationId) {
    createApplicationService.deleteApplication(applicationId);
  }
}
