package org.example.domain.select_question.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.Application;
import org.example.domain.field.service.CreateFieldService;
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
  private final CreateFieldService createFieldService;

  /**
   * 객관식 문항 생성
   */
  public void createSelectQuestion(Application application, List<CreateSelectQuestionRequest> requestList) {
    List<SelectQuestion> selectQuestionList = new ArrayList<>();
    for (CreateSelectQuestionRequest request : requestList) {

      // 객관식 문항 틀
      SelectQuestion selectQuestion = selectQuestionRepository.save(
        SelectQuestion.builder()
          .application(application)
          .question(request.question())
          .isRequired(request.isRequired())
          .isMultiSelect(request.isMultiSelect())
          .sequence(request.sequence())
          .build()
      );
      selectQuestionList.add(selectQuestion);
      createFieldService.createField(selectQuestion, request.createFieldRequestList());
    }

    application.setSelectQuestionList(selectQuestionList);
  }

  /**
   * 객관식 문항 복사
   */
  public void copySelectQuestion(Application newApplication, List<SelectQuestion> selectQuestionList) {
    List<SelectQuestion> newSelectQuestionList = new ArrayList<>();
    for (SelectQuestion selectQuestion : selectQuestionList) {

      // 객관식 문항 틀
      SelectQuestion newSelectQuestion = selectQuestionRepository.save(
        SelectQuestion.builder()
          .application(newApplication)
          .question(selectQuestion.getQuestion())
          .isRequired(selectQuestion.getIsRequired())
          .isMultiSelect(selectQuestion.getIsMultiSelect())
          .build()
      );
      newSelectQuestionList.add(newSelectQuestion);
      createFieldService.copyField(newSelectQuestion, selectQuestion.getFieldList());
    }

    newApplication.setSelectQuestionList(newSelectQuestionList);
  }
}
