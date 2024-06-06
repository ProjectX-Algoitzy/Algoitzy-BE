package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.domain.study.repository.StudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final StudyRepository studyRepository;
  private final ListStudyRepository listStudyRepository;

  /**
   * 정규 스터디 생성
   */
  public void createRegularStudy(CreateRegularStudyRequest request) {
    studyRepository.findByNameAndTypeIs(request.name(), StudyType.REGULAR)
      .ifPresent(study -> {
        throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "동일한 이름의 정규 스터디가 존재합니다.");
      });

    studyRepository.save(
      Study.builder()
        .profileUrl(request.profileUrl())
        .name(request.name())
        .content(request.content())
        .type(StudyType.REGULAR)
        .generation(listStudyRepository.getMaxStudyGeneration())
        .build()
    );
  }
}
