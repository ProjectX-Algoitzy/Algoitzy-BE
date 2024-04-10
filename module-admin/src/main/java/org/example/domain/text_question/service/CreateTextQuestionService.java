package org.example.domain.text_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.Application;
import org.example.domain.text_question.TextQuestion;
import org.example.domain.text_question.controller.request.CreateTextQuestionRequest;
import org.example.domain.text_question.repository.TextQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTextQuestionService {

  private final TextQuestionRepository textQuestionRepository;

  public void createTextQuestion(Application application, List<CreateTextQuestionRequest> requestList) {
    requestList
      .forEach(request -> textQuestionRepository.save(
        TextQuestion.builder()
          .application(application)
          .question(request.question())
          .build()
      ));
  }
}
