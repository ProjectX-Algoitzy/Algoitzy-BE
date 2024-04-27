package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.answer.Answer;
import org.example.domain.answer.repository.AnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreAnswerService {

  private final AnswerRepository answerRepository;

  public Answer findById(Long id) {
    return answerRepository.findById(id)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 answer id 입니다."));
  }
}
