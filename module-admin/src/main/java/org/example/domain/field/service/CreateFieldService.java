package org.example.domain.field.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.Field;
import org.example.domain.field.controller.request.CreateFieldRequest;
import org.example.domain.field.repository.FieldRepository;
import org.example.domain.select_question.SelectQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateFieldService {

  private final FieldRepository fieldRepository;

  /**
   * 객관식 문항 필드 생성
   */
  public void createField(SelectQuestion selectQuestion, List<CreateFieldRequest> requestList) {

    List<Field> fieldList = requestList.stream()
      .map(request -> fieldRepository.save(
        Field.builder()
          .selectQuestion(selectQuestion)
          .context(request.context())
          .build()
      )).toList();

    //양방향
    selectQuestion.setFieldList(fieldList);
  }

  /**
   * 객관식 문항 필드 복사
   */
  public void copyField(SelectQuestion selectQuestion, List<Field> fieldList) {
    List<Field> newFieldList = fieldList.stream()
      .map(field -> fieldRepository.save(
        Field.builder()
          .selectQuestion(selectQuestion)
          .context(field.getContext())
          .build()
      )).toList();

    //양방향
    selectQuestion.setFieldList(newFieldList);
  }
}
