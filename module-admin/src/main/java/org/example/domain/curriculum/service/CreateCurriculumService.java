package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.curriculum.controller.request.CreateCurriculumRequest;
import org.example.domain.curriculum.repository.CurriculumRepository;
import org.example.domain.study.service.CoreStudyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateCurriculumService {

  private final CoreStudyService coreStudyService;
  private final CurriculumRepository curriculumRepository;

  /**
   * 커리큘럼 생성
   */
  public void createCurriculum(CreateCurriculumRequest request) {
    curriculumRepository.save(
      Curriculum.builder()
        .study(coreStudyService.findById(request.studyId()))
        .title(request.title())
        .week(request.week())
        .content(request.content())
        .build()
    );
  }
}
