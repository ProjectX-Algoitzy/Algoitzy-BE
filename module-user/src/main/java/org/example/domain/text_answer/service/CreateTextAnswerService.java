package org.example.domain.text_answer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.Answer;
import org.example.domain.text_answer.TextAnswer;
import org.example.domain.text_answer.controller.request.CreateTextAnswerRequest;
import org.example.domain.text_answer.repository.TextAnswerRepository;
import org.example.domain.text_question.service.CoreTextQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTextAnswerService {

  private final CoreTextQuestionService coreTextQuestionService;
  private final TextAnswerRepository textAnswerRepository;

  /**
   * 주관식 답변 생성
   */
  public void createTextAnswer(Answer answer, List<CreateTextAnswerRequest> requestList) {
    List<TextAnswer> textAnswerList = requestList.stream()
      .map(request -> textAnswerRepository.save(TextAnswer.builder()
        .answer(answer)
        .textQuestion(coreTextQuestionService.findById(request.textQuestionId()))
        .text(request.text())
        .build())
      ).toList();

    answer.setTextAnswerList(textAnswerList);
  }
}
