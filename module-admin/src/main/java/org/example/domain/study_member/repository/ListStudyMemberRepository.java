package org.example.domain.study_member.repository;

import static org.example.domain.member.QMember.member;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberDto;
import org.example.domain.member.enums.Role;
import org.example.domain.study.Study;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.controller.request.AddRegularStudyMemberRequest;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListStudyMemberRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListAttendanceDto> getStudyMemberList(Long studyId) {
    return queryFactory
      .select(Projections.fields(
          ListAttendanceDto.class,
          studyMember.member.name,
          studyMember.member.handle
        )
      )
      .from(studyMember)
      .where(
        studyMember.study.id.eq(studyId),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .orderBy(
        studyMember.member.name.asc()
      )
      .fetch();
  }

  /**
   * 정규 스터디 스터디원 추가 대상 목록 조회
   */
  public Page<ListMemberDto> getNonStudyMemberList(Long studyId, SearchMemberRequest request) {
    List<ListMemberDto> memberList = queryFactory
      .select(Projections.fields(
          ListMemberDto.class,
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
        member.blockYN.isFalse(),
        isNotStudyMember(studyId, null),
        isRegularStudyMember(null),
        searchKeyword(request.searchKeyword())
      )
      .orderBy(member.name.asc())
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(member.count())
      .from(member)
      .where(
        member.role.eq(Role.ROLE_USER),
        member.blockYN.isFalse(),
        isNotStudyMember(studyId, null),
        isRegularStudyMember(null),
        searchKeyword(request.searchKeyword())
      );

    return PageableExecutionUtils.getPage(memberList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 해당 스터디 지원 이력 없는 멤버
   */
  private Predicate isNotStudyMember(Long studyId, Long memberId) {
    return member.notIn(
      queryFactory
        .select(studyMember.member)
        .from(studyMember)
        .where(
          studyMember.study.id.eq(studyId),
          (memberId == null) ? null : studyMember.member.id.eq(memberId)
        )
    );
  }

  /**
   * 정규 스터디 참여 이력 있는 멤버
   */
  private Predicate isRegularStudyMember(Long memberId) {
    return member.in(
      queryFactory
        .select(studyMember.member)
        .from(studyMember)
        .where(
          studyMember.study.type.eq(StudyType.REGULAR),
          studyMember.status.eq(StudyMemberStatus.PASS),
          (memberId == null) ? null : studyMember.member.id.eq(memberId)
        )
    );
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

  /**
   * 정규 스터디 스터디원 추가 가능 여부 반환
   */
  public boolean canAddRegularStudyMember(Long studyId, AddRegularStudyMemberRequest request) {
    return !queryFactory
      .selectFrom(member)
      .where(
        member.role.eq(Role.ROLE_USER),
        member.blockYN.isFalse(),
        isNotStudyMember(studyId, request.memberId()),
        isRegularStudyMember(request.memberId())
      )
      .fetch()
      .isEmpty();
  }
}
