package org.example.domain.study_member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.study.Study;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateStudyMemberService {

  private final StudyMemberRepository studyMemberRepository;

  /**
   * 스터디원 생성
   */
  public void createStudyMember(Study study, Member member) {
    studyMemberRepository.save(
      StudyMember.builder()
        .study(study)
        .member(member)
        .role(StudyMemberRole.MEMBER)
        .status(StudyMemberStatus.DOCUMENT)
        .build()
    );
  }
}
