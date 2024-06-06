package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreateMemberService createMemberService;
  private final ListMemberService listMemberService;

  /**
   * 유저 역할 변경
   */
  public void updateMemberRole(Long memberId) {
    createMemberService.updateMemberRole(memberId);
  }

  /**
   * 관리자 목록 조회
   */
  public ListMemberResponse getAdminMemberList() {
    return listMemberService.getAdminMemberList();
  }

  /**
   * 스터디원 목록 조회
   */
  public ListMemberResponse getUserMemberList(SearchMemberRequest request) {
    return listMemberService.getUserMemberList(request);
  }
}
