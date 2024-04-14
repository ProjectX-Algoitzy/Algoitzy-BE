package org.example.domain.text_answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.text_answer.repository.TextAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateTextAnswerService {

  private final TextAnswerRepository textAnswerRepository;

  public void createTextAnswer() {

  }
}
