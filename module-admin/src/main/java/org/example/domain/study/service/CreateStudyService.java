package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.controller.request.UpdateStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.StudyRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final CoreStudyService coreStudyService;
  private final StudyRepository studyRepository;
  private final GenerationRepository generationRepository;

  @Value("${s3.basic-image.study}")
  private String basicStudyImage;

  /**
   * 정규 스터디 생성
   */
  public void createRegularStudy(CreateRegularStudyRequest request) {
    if (studyRepository.findByNameAndTypeIs(request.name(), StudyType.REGULAR).isPresent()) {
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "동일한 이름의 정규 스터디가 존재합니다.");
    }

    String profileUrl = (StringUtils.hasText(request.profileUrl())) ? request.profileUrl() : basicStudyImage;
    studyRepository.save(
      Study.builder()
        .profileUrl(profileUrl)
        .name(request.name())
        .content(request.content())
        .type(StudyType.REGULAR)
        .generation(generationRepository.findTopByOrderByValueDesc())
        .build()
    );
  }

  /**
   * 스터디 수정
   */
  public void updateStudy(Long studyId, UpdateStudyRequest request) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디는 수정할 수 없습니다.");
    }

    String profileUrl = (StringUtils.hasText(request.profileUrl())) ? request.profileUrl() : basicStudyImage;
    study.update(profileUrl, request.name(), request.content());
  }
}
