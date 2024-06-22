package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.curriculum.controller.request.CreateCurriculumRequest;
import org.example.domain.curriculum.controller.request.UpdateCurriculumRequest;
import org.example.domain.curriculum.repository.CoreCurriculumService;
import org.example.domain.curriculum.repository.CurriculumRepository;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCurriculumService {

  private final CoreCurriculumService coreCurriculumService;
  private final CoreStudyService coreStudyService;
  private final CurriculumRepository curriculumRepository;

  /**
   * 커리큘럼 생성
   */
  public void createCurriculum(CreateCurriculumRequest request) {
    Study study = coreStudyService.findById(request.studyId());
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디는 커리큘럼을 생성할 수 없습니다.");
    }

    curriculumRepository.save(
      Curriculum.builder()
        .study(study)
        .title(request.title())
        .week(request.week())
        .content(request.content())
        .build()
    );
  }

  /**
   * 커리큘럼 수정
   */
  public void updateCurriculum(Long curriculumId, UpdateCurriculumRequest request) {
    Curriculum curriculum = coreCurriculumService.findById(curriculumId);
    Study study = null;
    if (request.studyId() != null) {
      study = coreStudyService.findById(request.studyId());
      if (study.getType().equals(StudyType.TEMP)) {
        throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디는 커리큘럼을 생성할 수 없습니다.");
      }
    }
    curriculum.update(
      study,
      request.title(),
      request.week(),
      request.content()
    );
  }

  /**
   * 커리큘럼 삭제
   */
  public void deleteCurriculum(Long curriculumId) {
    Curriculum curriculum = coreCurriculumService.findById(curriculumId);
    curriculumRepository.delete(curriculum);
  }
}
