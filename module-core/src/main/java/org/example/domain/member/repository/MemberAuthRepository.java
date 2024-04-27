package org.example.domain.member.repository;


import static org.example.domain.member.QMember.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.enums.Role;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberAuthRepository {

  /**
   * ADMIN : 전부
   * USER : 자신
   */
  public BooleanExpression emailEq() {
    Role role = SecurityUtils.getCurrentMemberRole();
    return (role.equals(Role.ROLE_ADMIN)) ? null : member.email.eq(SecurityUtils.getCurrentMemberEmail());
  }
}
