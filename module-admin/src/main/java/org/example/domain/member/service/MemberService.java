package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final CreateMemberService createMemberService;

  /**
   * 유저 역할 변경
   */
  public void updateMemberRole(Long memberId) {
    createMemberService.updateMemberRole(memberId);
  }
}
