package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.ListRegularStudyResponse;
import org.example.domain.study.repository.ListStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudyService {

  private final ListStudyRepository listStudyRepository;

  /**
   * 정규 스터디 목록 조회
   */
  public ListRegularStudyResponse getRegularStudyList() {
    return ListRegularStudyResponse.builder()
      .studyList(listStudyRepository.getRegularStudyList(false))
      .build();
  }
}
