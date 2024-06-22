package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.request.SearchCurriculumRequest;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.repository.ListCurriculumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListCurriculumService {

  private final ListCurriculumRepository listCurriculumRepository;

  /**
   * 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(SearchCurriculumRequest request) {
    return ListCurriculumResponse.builder()
      .curriculumList(listCurriculumRepository.getCurriculumList(request))
      .build();
  }
}
