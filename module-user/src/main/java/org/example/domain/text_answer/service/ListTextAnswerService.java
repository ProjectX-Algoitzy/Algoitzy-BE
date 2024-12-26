package org.example.domain.text_answer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_answer.controller.response.DetailTextAnswerDto;
import org.example.domain.text_answer.repository.DetailTextAnswerRepository;
import org.example.domain.text_answer.repository.ListTextAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListTextAnswerService {

  private final ListTextAnswerRepository listTextAnswerRepository;
  private final DetailTextAnswerRepository detailTextAnswerRepository;

  /**
   * 지원서 상세 주관식 답변 목록 조회
   */
  public List<DetailTextAnswerDto> getTextAnswerList(Long applicationId, Long answerId) {
    List<DetailTextAnswerDto> textQuestionList = listTextAnswerRepository.getTextQuestionList(applicationId);
    for (DetailTextAnswerDto dto : textQuestionList) {
      String textAnswer = detailTextAnswerRepository.getTextAnswer(dto.getTextQuestionId(), answerId);
      if (!StringUtils.hasText(textAnswer)) continue;
      dto.setText(textAnswer);
    }
    return textQuestionList;
  }
}
