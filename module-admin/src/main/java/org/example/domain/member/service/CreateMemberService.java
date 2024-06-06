package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMemberService {

  private final CoreMemberService coreMemberService;

  /**
   * 유저 역할 변경
   */
  public void updateMemberRole(Long memberId) {
    Member member = coreMemberService.findById(memberId);
    member.updateRole();
  }
}
