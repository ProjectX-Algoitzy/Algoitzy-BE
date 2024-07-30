package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.study.Study;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.DetailStudyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailStudyService {

  private final CoreStudyService coreStudyService;
  private final DetailStudyRepository detailStudyRepository;

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디가 아닙니다.");
    }
    return detailStudyRepository.getTempStudy(studyId);
  }

  /**
   * 정규 스터디 정보 조회
   */
  public RegularStudyInfoResponse getRegularStudyInfo(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }
    return detailStudyRepository.getRegularStudyInfo(studyId);
  }

  /**
   * 정규 스터디 홈 조회
   */
  public String getContent(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }

    return coreStudyService.findById(studyId).getContent();
  }

}
