package org.example.domain.study.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.controller.response.DetailTempStudyResponse;
import org.example.domain.study.controller.response.RegularStudyInfoResponse;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.repository.DetailStudyRepository;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailStudyService {

  private final CoreMemberService coreMemberService;
  private final CoreStudyService coreStudyService;
  private final DetailStudyRepository detailStudyRepository;
  private final DetailStudyMemberRepository detailStudyMemberRepository;

  /**
   * 자율 스터디 상세 조회
   */
  public DetailTempStudyResponse getTempStudy(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자율 스터디가 아닙니다.");
    }

    if (!detailStudyMemberRepository.isRegularStudyMember()
      && coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()).getRole().equals(Role.ROLE_USER)) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "정규 스터디원만 열람할 수 있습니다.");
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
