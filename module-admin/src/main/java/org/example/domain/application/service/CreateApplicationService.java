package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.application.controller.request.CopyApplicationRequest;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.application.controller.request.UpdateApplicationRequest;
import org.example.domain.application.Application;
import org.example.domain.application.controller.response.CreateApplicationResponse;
import org.example.domain.application.repository.ApplicationRepository;
import org.example.domain.select_question.service.CreateSelectQuestionService;
import org.example.domain.study.Study;
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
  public CreateApplicationResponse createApplication(CreateApplicationRequest request) {
    Study study = coreStudyService.findById(request.studyId());
    if (applicationRepository.findByStudy(study).isPresent())
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 지원서가 존재하는 스터디입니다.");

    Application application = applicationRepository.save(
      Application.builder()
        .title(study.getName() + " 지원서")
        .study(study)
        .build()
    );
    createSelectQuestionService.createSelectQuestion(application);

    return CreateApplicationResponse.builder()
      .applicationId(application.getId())
      .build();
  }

  /**
   * 지원서 저장
   */
  public void updateApplication(Long applicationId, UpdateApplicationRequest request) {
    if (coreApplicationService.findById(applicationId).getConfirmYN()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "확정된 지원서는 수정할 수 없습니다.");
    }

    // todo delete query 성능 개선
    applicationRepository.deleteById(applicationId);

    Application application = applicationRepository.save(
      Application.builder()
        .study(coreStudyService.findById(request.studyId()))
        .title(request.title())
        .confirmYN(request.confirmYN())
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
   * 지원서 복사
   */
  public void renewApplication(Study newStudy, Application oldApplication) {
    Application newApplication = applicationRepository.save(Application.builder()
      .study(newStudy)
      .title(oldApplication.getTitle())
      .confirmYN(false)
      .build()
    );
    createTextQuestionService.copyTextQuestion(newApplication, oldApplication.getTextQuestionList());
    createSelectQuestionService.copySelectQuestion(newApplication, oldApplication.getSelectQuestionList());
  }

  /**
   * 지원서 삭제
   */
  public void deleteApplication(Long applicationId) {
    if (coreApplicationService.findById(applicationId).getConfirmYN()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "확정된 지원서는 삭제할 수 없습니다.");
    }
    applicationRepository.deleteById(applicationId);
  }
}
