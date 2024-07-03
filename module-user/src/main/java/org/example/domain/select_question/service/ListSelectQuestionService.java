package org.example.domain.select_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.response.DetailFieldDto;
import org.example.domain.field.service.CoreListFieldService;
import org.example.domain.select_question.controller.response.DetailSelectQuestionDto;
import org.example.domain.select_question.repository.ListSelectQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListSelectQuestionService {

  private final ListSelectQuestionRepository listSelectQuestionRepository;
  private final CoreListFieldService coreListFieldService;

  /**
   * 지원서 양식 객관식 문항 상세 조회
   */
  public List<DetailSelectQuestionDto> getSelectQuestionList(Long applicationId) {
    List<DetailSelectQuestionDto> selectQuestionList = listSelectQuestionRepository.getSelectQuestionList(applicationId);
    for (int i = 0; i < selectQuestionList.size(); i++) {
      DetailSelectQuestionDto selectQuestion = selectQuestionList.get(i);
      List<DetailFieldDto> fieldList = coreListFieldService.getFieldList(selectQuestion.getSelectQuestionId());

      selectQuestionList.set(i, selectQuestion.toBuilder()
        .fieldList(fieldList)
        .build());
    }
    return selectQuestionList;
  }
}
