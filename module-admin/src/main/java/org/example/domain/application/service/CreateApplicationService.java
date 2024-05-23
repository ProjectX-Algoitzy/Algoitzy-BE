package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.UpdateApplicationRequest;
import org.example.domain.application.Application;
import org.example.domain.application.controller.response.CreateApplicationResponse;
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
  public CreateApplicationResponse createApplication() {
    Application application = applicationRepository.save(
      Application.builder()
        .title("새 지원서")
        .build()
    );
    createSelectQuestionService.createSelectQuestion(application);

    System.out.println("application.getId() = " + application.getId());
    return CreateApplicationResponse.builder()
      .applicationId(application.getId())
      .build();
  }

  /**
   * 지원서 임시저장
   */
  public void updateApplication(Long applicationId, UpdateApplicationRequest request) {
    // todo delete query 성능 개선
    applicationRepository.deleteById(applicationId);

    Application application = applicationRepository.save(
      Application.builder()
        .study(coreStudyService.findById(request.studyId()))
        .title(request.title())
        .build()
    );
    createTextQuestionService.updateTextQuestion(application, request.updateTextQuestionList());
    createSelectQuestionService.updateSelectQuestion(application, request.updateSelectQuestionList());
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
