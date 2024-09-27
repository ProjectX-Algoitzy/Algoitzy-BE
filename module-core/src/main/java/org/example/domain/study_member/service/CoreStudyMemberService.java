package org.example.domain.study_member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.repository.StudyMemberRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreStudyMemberService {

  private final StudyMemberRepository studyMemberRepository;

  public StudyMember findById(Long studyMemberId) {
    return studyMemberRepository.findById(studyMemberId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 스터디원 ID 입니다."));
  }

}
