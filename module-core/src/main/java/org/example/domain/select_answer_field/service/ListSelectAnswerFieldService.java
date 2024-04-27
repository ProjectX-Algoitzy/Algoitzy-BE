package org.example.domain.select_answer_field.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.answer.Answer;
import org.example.domain.select_answer_field.SelectAnswerField;
import org.example.domain.select_answer_field.repository.ListSelectAnswerFieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListSelectAnswerFieldService {

  private final ListSelectAnswerFieldRepository listSelectAnswerFieldRepository;

  public List<SelectAnswerField> getSelectAnswerFieldList(Answer answer) {
    return listSelectAnswerFieldRepository.getSelectAnswerFieldList(answer);
  }
}
