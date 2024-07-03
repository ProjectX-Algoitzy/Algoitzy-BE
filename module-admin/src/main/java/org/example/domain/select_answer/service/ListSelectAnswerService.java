package org.example.domain.select_answer.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.answer.Answer;
import org.example.domain.field.Field;
import org.example.domain.field.repository.FieldRepository;
import org.example.domain.select_answer.controller.response.DetailSelectAnswerDto;
import org.example.domain.select_answer_field.SelectAnswerField;
import org.example.domain.select_answer_field.response.DetailSelectAnswerFieldDto;
import org.example.domain.select_answer_field.service.ListSelectAnswerFieldService;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.select_question.repository.SelectQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ListSelectAnswerService {

  private final ListSelectAnswerFieldService listSelectAnswerFieldService;
  private final SelectQuestionRepository selectQuestionRepository;
  private final FieldRepository fieldRepository;

  /**
   * 지원서 상세 객관식 답변 목록 조회
   */
  public List<DetailSelectAnswerDto> getSelectAnswerList(Answer answer) {
    List<DetailSelectAnswerDto> selectAnswerList = new ArrayList<>();

    List<SelectQuestion> selectQuestionList = selectQuestionRepository.findAllByApplication(answer.getApplication());
    for (SelectQuestion selectQuestion : selectQuestionList) {
      // 모든 필드 조회
      List<Field> fieldList = fieldRepository.findAllBySelectQuestion(selectQuestion);

      // 선택한 필드 조회
      List<SelectAnswerField> selectAnswerFieldList = listSelectAnswerFieldService.getSelectAnswerFieldList(answer);
      List<Field> selectFieldList = selectAnswerFieldList.stream()
        .map(SelectAnswerField::getField)
        .toList();

      // 선택한 필드라면 isSelected true
      List<DetailSelectAnswerFieldDto> selectAnswerFieldDtoList = fieldList.stream()
        .map(field -> DetailSelectAnswerFieldDto.builder()
          .context(field.getContext())
          .isSelected(selectFieldList.contains(field))
          .build()
        ).toList();

      selectAnswerList.add(DetailSelectAnswerDto.builder()
        .question(selectQuestion.getQuestion())
        .isRequired(selectQuestion.getIsRequired())
        .isMultiSelect(selectQuestion.getIsMultiSelect())
        .selectAnswerFieldList(selectAnswerFieldDtoList)
        .build()
      );
    }

    return selectAnswerList;
  }
}
