package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

  private final ListCurriculumService listCurriculumService;
  private final DetailCurriculumService detailCurriculumService;

  /**
   * 정규 스터디 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    return listCurriculumService.getCurriculumList(studyId);
  }

  /**
   * 커리큘럼 상세 조회
   */
  public DetailCurriculumResponse getCurriculum(Long curriculumId) {
    return detailCurriculumService.getCurriculum(curriculumId);
  }
}
