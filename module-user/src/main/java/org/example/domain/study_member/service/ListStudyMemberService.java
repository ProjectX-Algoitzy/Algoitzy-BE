package org.example.domain.study_member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.study.Study;
import org.example.domain.study.service.CoreStudyService;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.controller.response.ListTempStudyMemberDto;
import org.example.domain.study_member.controller.response.ListTempStudyMemberResponse;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.DetailStudyMemberRepository;
import org.example.domain.study_member.repository.ListStudyMemberRepository;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListStudyMemberService {

  private final CoreMemberService coreMemberService;
  private final CoreStudyService coreStudyService;
  private final ListStudyMemberRepository listStudyMemberRepository;
  private final DetailStudyMemberRepository detailStudyMemberRepository;
  private final StudyMemberRepository studyMemberRepository;

  /**
   * 자율 스터디원 목록 조회
   */
  public ListTempStudyMemberResponse getTempStudyMemberList(Long studyId) {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    Study study = coreStudyService.findById(studyId);
    if (studyMemberRepository.findByStudyAndMemberAndStatus(study, member, StudyMemberStatus.PASS).isEmpty()) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "스터디원만 열람할 수 있습니다.");
    }

    List<ListTempStudyMemberDto> studyMemberList = listStudyMemberRepository.getTempStudyMemberList(studyId);

    // 스터디장 아닌 경우 번호 미노출 및 수락된 스터디원만 노출
    StudyMember leader = detailStudyMemberRepository.getTempStudyLeader(studyId);
    if (!leader.getMember().getEmail().equals(SecurityUtils.getCurrentMemberEmail())) {
      studyMemberList = studyMemberList
        .stream().filter(studyMember -> studyMember.getStatus().equals(StudyMemberStatus.PASS))
        .peek(ListTempStudyMemberDto::blindPhoneNumber)
        .toList();
    }

    return ListTempStudyMemberResponse.builder()
      .studyMemberList(studyMemberList)
      .build();
  }
}
