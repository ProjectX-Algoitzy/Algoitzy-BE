package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.RefreshAccessTokenRequest;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.enums.Role;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final LoginService loginService;
  private final CreateMemberService createMemberService;
  private final ListMemberService listMemberService;
  private final DetailMemberService detailMemberService;

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

  /**
   * 로그인
   */
  public LoginResponse login(Role role, LoginRequest request) {
    return loginService.login(role, request);
  }

  /**
   * 로그아웃
   */
  public void logout() {
    loginService.logout();
  }

  /**
   * Access Token 재발급
   */
  public LoginResponse refreshAccessToken(RefreshAccessTokenRequest request) {
    return loginService.refreshAccessToken(request);
  }

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return detailMemberService.getMemberInfo();
  }
}
