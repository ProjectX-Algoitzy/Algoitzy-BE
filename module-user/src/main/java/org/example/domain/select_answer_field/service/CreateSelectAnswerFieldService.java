package org.example.domain.select_answer_field.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.Field;
import org.example.domain.field.service.CoreFieldService;
import org.example.domain.select_answer.SelectAnswer;
import org.example.domain.select_answer_field.SelectAnswerField;
import org.example.domain.select_answer_field.repository.SelectAnswerFieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSelectAnswerFieldService {

  private final CoreFieldService coreFieldService;
  private final SelectAnswerFieldRepository selectAnswerFieldRepository;

  public void createSelectAnswerField(SelectAnswer selectAnswer, List<Long> fieldIdList) {
    List<Field> fieldList = fieldIdList.stream().map(coreFieldService::findById).toList();
    fieldList
      .forEach(field -> selectAnswerFieldRepository.save(SelectAnswerField.builder()
        .selectAnswer(selectAnswer)
        .field(field)
        .build()));
  }
}