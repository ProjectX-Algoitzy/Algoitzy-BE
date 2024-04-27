package org.example.domain.text_answer.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.text_answer.repository.ListTextAnswerRepository;
import org.example.domain.text_answer.response.DetailTextAnswerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListTextAnswerService {

  private final ListTextAnswerRepository listTextAnswerRepository;

  public List<DetailTextAnswerDto> getTextAnswerList(Long answerId) {
    return listTextAnswerRepository.getTextAnswerList(answerId);
  }
}
