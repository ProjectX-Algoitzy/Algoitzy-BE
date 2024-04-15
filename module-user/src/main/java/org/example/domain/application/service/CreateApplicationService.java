package org.example.domain.application.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.CreateApplicationRequest;
import org.example.domain.select_answer.service.CreateSelectAnswerService;
import org.example.domain.text_answer.service.CreateTextAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateApplicationService {

  private final CreateTextAnswerService createTextAnswerService;
  private final CreateSelectAnswerService createSelectAnswerService;

  /**
   * 지원서 작성
   */
  public void createApplication(CreateApplicationRequest request) {
    createTextAnswerService.createTextAnswer(request.createTextAnswerRequestList());
    createSelectAnswerService.createSelectAnswer(request.createSelectAnswerRequestList());
  }
}
