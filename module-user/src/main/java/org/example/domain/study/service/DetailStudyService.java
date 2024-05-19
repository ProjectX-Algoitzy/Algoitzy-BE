package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.repository.DetailStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailStudyService {

  private final DetailStudyRepository detailStudyRepository;

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    return detailStudyRepository.getTempStudy(studyId);
  }
}
