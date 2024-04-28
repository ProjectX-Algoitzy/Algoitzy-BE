package org.example.domain.select_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.response.DetailFieldDto;
import org.example.domain.field.service.CoreListFieldService;
import org.example.domain.select_question.repository.CoreListSelectQuestionRepository;
import org.example.domain.select_question.response.DetailSelectQuestionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreListSelectQuestionService {

  private final CoreListSelectQuestionRepository coreListSelectQuestionRepository;
  private final CoreListFieldService coreListFieldService;

  /**
   * 지원서 양식 객관식 문항 상세 조회
   */
  public List<DetailSelectQuestionDto> getSelectQuestionList(Long applicationId) {
    List<DetailSelectQuestionDto> selectQuestionList = coreListSelectQuestionRepository.getSelectQuestionList(applicationId);
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
