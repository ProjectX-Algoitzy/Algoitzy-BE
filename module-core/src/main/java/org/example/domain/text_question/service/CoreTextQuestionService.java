package org.example.domain.text_question.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.text_question.TextQuestion;
import org.example.domain.text_question.repository.TextQuestionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreTextQuestionService {

  private final TextQuestionRepository textQuestionRepository;

  public TextQuestion findById(Long id) {
    return textQuestionRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 text question id 입니다."));
  }
}
