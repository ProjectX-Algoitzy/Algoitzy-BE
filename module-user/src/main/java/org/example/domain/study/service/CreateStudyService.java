package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.s3_file.service.CoreS3FileService;
import org.example.domain.study.Study;
import org.example.domain.study.controller.request.CreateTempStudyRequest;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.domain.study.repository.StudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyService {

  private final CoreS3FileService coreS3FileService;
  private final StudyRepository studyRepository;
  private final ListStudyRepository listStudyRepository;

  /**
   * 자율 스터디 생성
   */
  public void createTempStudy(CreateTempStudyRequest request) {
    if (StringUtils.hasText(request.imageUrl())) {
      coreS3FileService.findByFileUrl(request.imageUrl());
    }

    studyRepository.save(
      Study.builder()
        .imageUrl(request.imageUrl())
        .name(request.name())
        .memberLimit(request.memberLimit())
        .way(request.way())
        .subject(request.subject())
        .type(StudyType.TEMP)
        .target(request.target())
        .generation(listStudyRepository.getMaxStudyGeneration())
        .build()
    );
  }
}
