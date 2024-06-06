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
   * 커리큘럼 목록 조회(드롭박스용)
   */
  public ListRegularStudyResponse getRegularStudyList() {
    return ListRegularStudyResponse.builder()
      .studyList(listStudyRepository.getRegularStudyList())
      .build();
  }
}
