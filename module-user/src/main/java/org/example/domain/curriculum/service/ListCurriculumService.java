package org.example.domain.curriculum.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.curriculum.controller.response.ListCurriculumResponse;
import org.example.domain.curriculum.repository.ListCurriculumRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListCurriculumService {

  private final ListCurriculumRepository listCurriculumRepository;
  private final CoreStudyService coreStudyService;
  private final CoreMemberService coreMemberService;
  private final StudyMemberRepository studyMemberRepository;

  /**
   * 정규 스터디 커리큘럼 목록 조회
   */
  public ListCurriculumResponse getCurriculumList(Long studyId) {
    Study study = coreStudyService.findById(studyId);
    if (study.getType().equals(StudyType.TEMP)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    if (studyMemberRepository.findByStudyAndMemberAndStatus(study, member, StudyMemberStatus.PASS).isEmpty()) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디원만 열람할 수 있습니다.");
    }

    return ListCurriculumResponse
      .builder()
      .curriculumList(listCurriculumRepository.getCurriculumList(studyId))
      .build();
  }
}
