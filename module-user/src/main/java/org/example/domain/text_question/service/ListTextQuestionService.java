package org.example.domain.text_question.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_question.controller.response.DetailTextQuestionDto;
import org.example.domain.text_question.repository.ListTextQuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListTextQuestionService {

  private final ListTextQuestionRepository listTextQuestionRepository;

  /**
   * 지원서 양식 주관식 문항 상세 조회
   */
  public List<DetailTextQuestionDto> getTextQuestionList(Long applicationId) {
    return listTextQuestionRepository.getTextQuestionList(applicationId);
  }
}
