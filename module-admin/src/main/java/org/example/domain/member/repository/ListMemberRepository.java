package org.example.domain.member.repository;

import static org.example.domain.member.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberDto;
import org.example.domain.member.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListMemberRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 관리자 목록 조회
   */
  public List<ListMemberDto> getAdminMemberList() {
    return queryFactory
      .select(Projections.fields(ListMemberDto.class,
          member.id.as("memberId"),
          member.name,
          member.handle,
          member.major,
          member.role
        )
      )
      .from(member)
      .where(member.role.ne(Role.ROLE_USER))
      .orderBy(member.name.asc())
      .fetch();
  }

  /**
   * 스터디원 목록 조회
   */
  public Page<ListMemberDto> getUserMemberList(SearchMemberRequest request) {
    List<ListMemberDto> memberList = queryFactory
      .select(Projections.fields(ListMemberDto.class,
          member.id.as("memberId"),
          member.name,
          member.handle,
          member.major,
          member.role
        )
      )
      .from(member)
      .where(
        member.role.eq(Role.ROLE_USER),
        searchKeyword(request.searchKeyword())
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .orderBy(member.name.asc())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(member.count())
      .from(member)
      .where(
        member.role.eq(Role.ROLE_USER),
        searchKeyword(request.searchKeyword())
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize());

    return PageableExecutionUtils.getPage(memberList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 키워드 검색(이름, 백준 닉네임)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(member.name.contains(searchKeyword));
    builder.or(member.handle.contains(searchKeyword));
    return builder;
  }
}
