package org.example.domain.study_member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.controller.request.AddRegularStudyMemberRequest;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.ListStudyMemberRepository;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyMemberService {

  private final CoreStudyService coreStudyService;
  private final CoreMemberService coreMemberService;
  private final ListStudyMemberRepository listStudyMemberRepository;
  private final StudyMemberRepository studyMemberRepository;

  /**
   * 정규 스터디 스터디원 추가
   */
  public void addRegularStudyMember(Long studyId, AddRegularStudyMemberRequest request) {
    Study study = coreStudyService.findById(studyId);
    if (!study.getType().equals(StudyType.REGULAR)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "정규 스터디가 아닙니다.");
    }

    Member member = coreMemberService.findById(request.memberId());
    if (!listStudyMemberRepository.canAddRegularStudyMember(studyId, request)) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "추가할 수 없는 회원입니다.");
    }

    studyMemberRepository.save(
      StudyMember.builder()
        .study(study)
        .member(member)
        .role(StudyMemberRole.MEMBER)
        .status(StudyMemberStatus.PASS)
        .build()
    );

  }
}
