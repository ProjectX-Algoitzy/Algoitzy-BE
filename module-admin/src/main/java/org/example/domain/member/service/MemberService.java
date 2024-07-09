package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.LoginRequest;
import org.example.domain.member.controller.request.RefreshAccessTokenRequest;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.request.UpdateMemberRoleRequest;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.member.controller.response.LoginResponse;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final LoginService loginService;
  private final CreateMemberService createMemberService;
  private final ListMemberService listMemberService;
  private final DetailMemberService detailMemberService;

  /**
   * 멤버 권한 변경
   */
  public void updateMemberRole(UpdateMemberRoleRequest request) {
    createMemberService.updateMemberRole(request);
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
  public LoginResponse login(LoginRequest request) {
    return loginService.login(request);
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
