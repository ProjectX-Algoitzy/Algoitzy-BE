package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.application.Application;
import org.example.domain.application.repository.ApplicationRepository;
import org.example.domain.study.repository.CoreStudyRepository;
import org.example.domain.text_question.service.CreateTextQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateApplicationService {

  private final CoreStudyRepository coreStudyRepository;
  private final ApplicationRepository applicationRepository;
  private final CreateTextQuestionService createTextQuestionService;


  /**
   * 지원서 생성
   */
  public void createApplication(CreateApplicationRequest request) {

    // 지원서 저장
    Application application = applicationRepository.save(Application.builder()
      .study(coreStudyRepository.findById(request.studyId()))
      .title(request.title())
      .build()
    );

    //주관식
    createTextQuestionService.createTextQuestion(application, request.createTextQuestionRequestList());

    //객관식

    //객관식 필드
  }
}
