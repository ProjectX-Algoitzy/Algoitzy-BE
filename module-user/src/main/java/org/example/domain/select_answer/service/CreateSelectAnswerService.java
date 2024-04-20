package org.example.domain.select_answer.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.Answer;
import org.example.domain.select_answer.SelectAnswer;
import org.example.domain.select_answer.controller.request.CreateSelectAnswerRequest;
import org.example.domain.select_answer.repository.SelectAnswerRepository;
import org.example.domain.select_answer_field.service.CreateSelectAnswerFieldService;
import org.example.domain.select_question.service.CoreSelectQuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSelectAnswerService {

  private final CoreSelectQuestionService coreSelectQuestionService;
  private final SelectAnswerRepository selectAnswerRepository;
  private final CreateSelectAnswerFieldService createSelectAnswerFieldService;

  /**
   * 객관식 문항 생성
   */
  public void createSelectAnswer(Answer answer, List<CreateSelectAnswerRequest> requestList) {
    List<SelectAnswer> selectAnswerList = new ArrayList<>();
    for (CreateSelectAnswerRequest request : requestList) {

      SelectAnswer selectAnswer = selectAnswerRepository.save(
        SelectAnswer.builder()
          .answer(answer)
          .selectQuestion(coreSelectQuestionService.findById(request.selectQuestionId()))
          .build()
      );
      selectAnswerList.add(selectAnswer);
      createSelectAnswerFieldService.createSelectAnswerField(selectAnswer, request.fieldIdList());
    }

    answer.setSelectAnswerList(selectAnswerList);
  }

}
