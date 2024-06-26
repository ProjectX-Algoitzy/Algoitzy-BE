package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

  private final ListCurriculumService listCurriculumService;

  /**
   * 정규 스터디 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    return listCurriculumService.getCurriculumList(studyId);
  }
}
