package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.CreateAnswerRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final CreateAnswerService createAnswerService;

  /**
   * 지원서 작성
   */
  public void createAnswer(Long applicationId, CreateAnswerRequest request) {
    createAnswerService.createAnswer(applicationId, request);
  }
}
