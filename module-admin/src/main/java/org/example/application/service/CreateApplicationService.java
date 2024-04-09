package org.example.application.service;

import lombok.RequiredArgsConstructor;
import org.example.application.controller.request.CreateApplicationRequest;
import org.example.application.controller.response.CreateApplicationResponse;
import org.example.domain.application.Application;
import org.example.domain.application.repository.ApplicationRepository;
import org.example.domain.study.repository.CoreStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateApplicationService {

  private final CoreStudyRepository coreStudyRepository;
  private final ApplicationRepository applicationRepository;


  public CreateApplicationResponse createApplication(CreateApplicationRequest request) {
    Application application = applicationRepository.save(Application.builder()
      .study(coreStudyRepository.findById(request.studyId()))
      .title(request.title())
      .build()
    );

    return CreateApplicationResponse.builder()
      .applicationId(application.getId())
      .build();
  }
}
