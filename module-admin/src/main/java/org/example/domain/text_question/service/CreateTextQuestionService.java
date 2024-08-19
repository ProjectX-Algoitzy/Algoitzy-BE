package org.example.domain.text_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.Application;
import org.example.domain.text_question.TextQuestion;
import org.example.domain.text_question.controller.request.UpdateTextQuestionRequest;
import org.example.domain.text_question.repository.TextQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTextQuestionService {

  private final TextQuestionRepository textQuestionRepository;

  /**
   * 주관식 문항 생성
   */
  public void updateTextQuestion(Application application, List<UpdateTextQuestionRequest> requestList) {
    List<TextQuestion> textQuestionList = requestList.stream()
      .map(request -> textQuestionRepository.save(
        TextQuestion.builder()
          .application(application)
          .question(request.question())
          .isRequired(request.isRequired())
          .sequence(request.sequence())
          .build()
      )).toList();

    application.setTextQuestionList(textQuestionList);
  }

  /**
   * 주관식 문항 복사
   */
  public void copyTextQuestion(Application newApplication, List<TextQuestion> textQuestionList) {
    List<TextQuestion> newTextQuestionList = textQuestionList.stream()
      .map(textQuestion -> textQuestionRepository.save(
        TextQuestion.builder()
          .application(newApplication)
          .question(textQuestion.getQuestion())
          .sequence(textQuestion.getSequence())
          .isRequired(textQuestion.getIsRequired())
          .build()
      )).toList();

    newApplication.setTextQuestionList(newTextQuestionList);
  }
}
