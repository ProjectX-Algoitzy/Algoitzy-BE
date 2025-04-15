package org.example.domain.member.repository;

import static org.example.domain.member.QMember.member;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.member.enums.Role;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreListMemberRepository {

  private final JPAQueryFactory queryFactory;

  public List<Member> getAdminList() {
    return queryFactory
      .selectFrom(member)
      .where(member.role.ne(Role.ROLE_USER))
      .fetch();
  }
}
