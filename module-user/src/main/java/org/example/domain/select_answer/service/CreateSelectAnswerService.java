package org.example.domain.select_answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.select_answer.repository.SelectAnswerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateSelectAnswerService {

  private final SelectAnswerRepository selectAnswerRepository;

  public void createSelectAnswer() {

  }
}
