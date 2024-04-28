package org.example.domain.field.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.repository.CoreListFieldRepository;
import org.example.domain.field.response.DetailFieldDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreListFieldService {

  private final CoreListFieldRepository coreListFieldRepository;

  public List<DetailFieldDto> getFieldList(Long selectQuestionId) {
    return coreListFieldRepository.getFieldList(selectQuestionId);
  }
}
