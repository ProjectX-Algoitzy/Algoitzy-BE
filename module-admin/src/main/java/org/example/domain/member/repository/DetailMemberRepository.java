package org.example.domain.member.repository;

import static org.example.domain.member.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailMemberRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return queryFactory
      .select(Projections.fields(
          MemberInfoResponse.class,
          member.profileUrl,
          member.name,
          member.role
        )
      )
      .from(member)
      .where(member.email.eq(SecurityUtils.getCurrentMemberEmail()))
      .fetchOne();
  }
}
