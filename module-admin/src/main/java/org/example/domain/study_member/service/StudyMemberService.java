package org.example.domain.study_member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyMemberService {

  private final ListStudyMemberService listStudyMemberService;

  /**
   * 정규 스터디 스터디원 추가 대상 목록 조회
   */
  public ListMemberResponse getNonStudyMemberList(Long studyId, SearchMemberRequest request) {
    return listStudyMemberService.getNonStudyMemberList(studyId, request);
  }
}
