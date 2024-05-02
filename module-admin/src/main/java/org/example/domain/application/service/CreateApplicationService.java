package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.application.Application;
import org.example.domain.application.repository.ApplicationRepository;
import org.example.domain.select_question.service.CreateSelectQuestionService;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.text_question.service.CreateTextQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateApplicationService {

  private final CoreStudyService coreStudyService;
  private final CoreApplicationService coreApplicationService;
  private final ApplicationRepository applicationRepository;
  private final CreateTextQuestionService createTextQuestionService;
  private final CreateSelectQuestionService createSelectQuestionService;


  /**
   * 지원서 생성
   */
  public void createApplication(CreateApplicationRequest request) {

    Application application = applicationRepository.save(
      Application.builder()
        .study(coreStudyService.findById(request.studyId()))
        .title(request.title())
        .build()
    );
    createTextQuestionService.createTextQuestion(application, request.createTextQuestionRequestList());
    createSelectQuestionService.createSelectQuestion(application, request.createSelectQuestionRequestList());
  }

  /**
   * 지원서 복사
   */
  public void copyApplication(CopyApplicationRequest request) {
    Application application = coreApplicationService.findById(request.applicationId());
    Application newApplication = applicationRepository.save(Application.builder()
      .study(application.getStudy())
      .title(application.getTitle() + "의 복사본")
      .build()
    );
    createTextQuestionService.copyTextQuestion(newApplication, application.getTextQuestionList());
    createSelectQuestionService.copySelectQuestion(newApplication, application.getSelectQuestionList());
  }

  /**
   * 지원서 삭제
   */
  public void deleteApplication(Long applicationId) {
    coreApplicationService.findById(applicationId);
    applicationRepository.deleteById(applicationId);
  }
}
