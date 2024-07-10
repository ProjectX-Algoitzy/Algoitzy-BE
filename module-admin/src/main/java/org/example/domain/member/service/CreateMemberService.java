package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.UpdateMemberRoleRequest;
import org.example.domain.member.enums.Role;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateMemberService {

  private final CoreMemberService coreMemberService;

  /**
   * 멤버 권한 변경
   */
  public void updateMemberRole(UpdateMemberRoleRequest request) {
    Member currentMember= coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    if (!currentMember.getRole().equals(Role.ROLE_OWNER)) {
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "KOALA 회장만 접근 가능합니다.");
    }

    // 회장은 한 명만 존재
    if (request.role().equals(Role.ROLE_OWNER)) currentMember.updateRole(Role.ROLE_ADMIN);
    Member targetMember = coreMemberService.findById(request.memberId());
    targetMember.updateRole(request.role());
  }
}
