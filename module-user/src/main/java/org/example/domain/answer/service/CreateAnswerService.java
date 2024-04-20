package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.Answer;
import org.example.domain.answer.controller.request.CreateAnswerRequest;
import org.example.domain.answer.repository.AnswerRepository;
import org.example.domain.application.service.CoreApplicationService;
import org.example.domain.select_answer.service.CreateSelectAnswerService;
import org.example.domain.text_answer.service.CreateTextAnswerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateAnswerService {

  private final CoreApplicationService coreApplicationService;
  private final CreateTextAnswerService createTextAnswerService;
  private final CreateSelectAnswerService createSelectAnswerService;
  private final AnswerRepository answerRepository;

  /**
   * 지원서 작성
   */
  public void createAnswer(Long applicationId, CreateAnswerRequest request) {
    Answer answer = answerRepository.save(
      Answer.builder()
        .application(coreApplicationService.findById(applicationId))
        .build()
    );

    createTextAnswerService.createTextAnswer(answer, request.createTextAnswerRequestList());
    createSelectAnswerService.createSelectAnswer(answer, request.createSelectAnswerRequestList());
  }
}
