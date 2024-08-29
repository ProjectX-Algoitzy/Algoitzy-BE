package org.example.domain.select_question.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.select_question.repository.SelectQuestionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreSelectQuestionService {

  private final SelectQuestionRepository selectQuestionRepository;

  public SelectQuestion findById(Long id) {
    return selectQuestionRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 select question id 입니다."));
  }
}
