package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.curriculum.controller.request.SearchCurriculumRequest;
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

  private final CoreStudyService coreStudyService;
  private final ListCurriculumRepository listCurriculumRepository;

  /**
   * 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(SearchCurriculumRequest request) {
    Study study = coreStudyService.findById(request.studyId());
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디는 커리큘럼이 존재하지 않습니다.");
    }
    return ListCurriculumResponse.builder()
      .curriculumList(listCurriculumRepository.getCurriculumList(request))
      .build();
  }
}
