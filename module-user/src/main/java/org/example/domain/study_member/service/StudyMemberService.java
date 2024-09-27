package org.example.domain.study_member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.study_member.controller.response.ListTempStudyMemberResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

  private final ListStudyMemberService listStudyMemberService;

  /**
   * 자율 스터디원 목록 조회
   */
  public ListTempStudyMemberResponse getTempStudyMemberList(Long studyId) {
    return listStudyMemberService.getTempStudyMemberList(studyId);
  }
}
