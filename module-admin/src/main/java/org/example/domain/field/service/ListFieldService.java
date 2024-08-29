package org.example.domain.field.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.controller.response.DetailFieldDto;
import org.example.domain.field.repository.ListFieldRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListFieldService {

  private final ListFieldRepository listFieldRepository;

  public List<DetailFieldDto> getFieldList(Long selectQuestionId) {
    return listFieldRepository.getFieldList(selectQuestionId);
  }
}
