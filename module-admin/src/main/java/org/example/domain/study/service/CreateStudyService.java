package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateRegularStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.domain.study.repository.StudyRepository;
import org.example.util.ValueUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final StudyRepository studyRepository;
  private final ListStudyRepository listStudyRepository;

  @Value("${s3.basic-image.coding-test-basic}")
  private String codingTestBasicImage;

  @Value("${s3.basic-image.coding-test-prepare}")
  private String codingTestPrepareImage;
  @Value("${s3.basic-image.study}")
  private String basicStudyImage;

  /**
   * 정규 스터디 생성
   */
  public void createRegularStudy(CreateRegularStudyRequest request) {
    if (studyRepository.findByNameAndTypeIs(request.name(), StudyType.REGULAR).isPresent()) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "동일한 이름의 정규 스터디가 존재합니다.");
    }

    String profileUrl = request.profileUrl();
    if (!StringUtils.hasText(profileUrl)) {
      if (request.name().equals(ValueUtils.CODING_TEST_BASIC)) profileUrl = codingTestBasicImage;
      else if (request.name().equals(ValueUtils.CODING_TEST_PREPARE)) profileUrl = codingTestPrepareImage;
      else profileUrl = basicStudyImage;
    }
    studyRepository.save(
      Study.builder()
        .profileUrl(profileUrl)
        .name(request.name())
        .content(request.content())
        .type(StudyType.REGULAR)
        .generation(listStudyRepository.getMaxStudyGeneration())
        .build()
    );
  }
}
