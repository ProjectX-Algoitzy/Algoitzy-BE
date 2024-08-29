package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.curriculum.repository.CurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreCurriculumService {

  private final CurriculumRepository curriculumRepository;

  public Curriculum findById(Long curriculumId) {
    return curriculumRepository.findById(curriculumId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 커리큘럼 ID입니다."));
  }
}
