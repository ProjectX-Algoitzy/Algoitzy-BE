package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListTempStudyResponse;
import org.example.domain.study.repository.ListStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudyService {

  private final ListStudyRepository listStudyRepository;

  /**
   * 자율 스터디 목록 조회
   */
  public ListTempStudyResponse getTempStudyList() {
    return ListTempStudyResponse.builder()
      .studyList(listStudyRepository.getTempStudyList())
      .build();
  }

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
