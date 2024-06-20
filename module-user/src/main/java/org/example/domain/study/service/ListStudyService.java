package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListStudyResponse;
import org.example.domain.study.enums.StudyType;
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
  public ListStudyResponse getTempStudyList() {
    return ListStudyResponse.builder()
      .studyList(listStudyRepository.getStudyList(StudyType.TEMP))
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

  /**
   * 정규 스터디 목록 조회
   */
  public ListStudyResponse getRegularStudyList() {
    return ListStudyResponse.builder()
      .studyList(listStudyRepository.getStudyList(StudyType.REGULAR))
      .build();
  }
}
