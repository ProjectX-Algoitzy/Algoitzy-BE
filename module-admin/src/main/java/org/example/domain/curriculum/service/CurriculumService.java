package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.request.CreateCurriculumRequest;
import org.example.domain.curriculum.controller.request.UpdateCurriculumRequest;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurriculumService {

  private final CreateCurriculumService createCurriculumService;
  private final DetailCurriculumService detailCurriculumService;
  private final ListCurriculumService listCurriculumService;

  /**
   * 커리큘럼 생성
   */
  public void createCurriculum(CreateCurriculumRequest request) {
    createCurriculumService.createCurriculum(request);
  }

  /**
   * 커리큘럼 상세 조회
   */
  public DetailCurriculumResponse getCurriculum(Long curriculumId) {
    return detailCurriculumService.getCurriculum(curriculumId);
  }

  /**
   * 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    return listCurriculumService.getCurriculumList(studyId);
  }

  /**
   * 커리큘럼 수정
   */
  public void updateCurriculum(Long curriculumId, UpdateCurriculumRequest request) {
    createCurriculumService.updateCurriculum(curriculumId, request);
  }

  /**
   * 커리큘럼 삭제
   */
  public void deleteCurriculum(Long curriculumId) {
    createCurriculumService.deleteCurriculum(curriculumId);
  }
}
