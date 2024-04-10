package org.example.domain.select_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.Application;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.select_question.controller.request.CreateSelectQuestionRequest;
import org.example.domain.select_question.repository.SelectQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSelectQuestionService {

  private final SelectQuestionRepository selectQuestionRepository;

  public void createSelectQuestion(Application application, List<CreateSelectQuestionRequest> requestList) {

    for (CreateSelectQuestionRequest request : requestList) {

      // 객관식 문항 틀
      SelectQuestion selectQuestion = selectQuestionRepository.save(
        SelectQuestion.builder()
          .application(application)
          .question(request.question())
          .build()
      );

      // 필드

    }
  }
}
