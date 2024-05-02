package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.repository.ListStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudyService {

  private final ListStudyRepository listStudyRepository;

  /**
   * 최신 기수 스터디 개수
   */
  public Integer getStudyCount() {
    return listStudyRepository.getStudyCount();
  }

  /**
   * 스터디 최신 기수
   */
  public Integer getMaxStudyGeneration() {
    return listStudyRepository.getMaxStudyGeneration();
  }
}
