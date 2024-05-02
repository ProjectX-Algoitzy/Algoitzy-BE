package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {

  private final ListStudyService listStudyService;

  /**
   * 최신 기수 스터디 개수
   */
  public Integer getStudyCount() {
    return listStudyService.getStudyCount();
  }

  /**
   * 스터디 최신 기수
   */
  public Integer getMaxStudyGeneration() {
    return listStudyService.getMaxStudyGeneration();
  }
}
