package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.repository.ListCurriculumRepository;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListCurriculumService {

  private final ListCurriculumRepository listCurriculumRepository;
  private final CoreStudyService coreStudyService;

  /**
   * 정규 스터디 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }

    return ListCurriculumResponse
      .builder()
      .curriculumList(listCurriculumRepository.getCurriculumList(studyId))
      .build();
  }
}
