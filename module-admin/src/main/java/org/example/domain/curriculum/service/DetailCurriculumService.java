package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.example.domain.curriculum.repository.DetailCurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailCurriculumService {

  private final DetailCurriculumRepository detailCurriculumRepository;

  /**
   * 커리큘럼 상세 조회
   */
  public DetailCurriculumResponse getCurriculum(Long curriculumId) {
    return detailCurriculumRepository.getCurriculum(curriculumId);
  }
}
