package org.example.domain.member.repository;

import static org.example.domain.member.QMember.member;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.controller.response.MyPageInfoResponse;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
import org.example.util.http_request.Url;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailMemberRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getLoginMemberInfo() {
    return queryFactory
      .select(Projections.fields(
        MemberInfoResponse.class,
        member.id.as("memberId"),
        member.profileUrl,
        member.name,
        member.handle,
        member.role,
        Expressions.as(JPAExpressions
            .selectFrom(studyMember)
            .where(
              studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail()),
              studyMember.status.eq(StudyMemberStatus.PASS),
              studyMember.study.type.eq(StudyType.REGULAR)
            ).exists()
          , "regularStudyMemberYn")
      ))
      .from(member)
      .where(member.email.eq(SecurityUtils.getCurrentMemberEmail()))
      .fetchOne();
  }

  /**
   * 마이페이지 멤버 정보
   */
  public MyPageInfoResponse getMyPageInfo(String handle) {
    return queryFactory
      .select(Projections.fields(
        MyPageInfoResponse.class,
        member.profileUrl,
        member.name,
        member.handle,
        new CaseBuilder()
          .when(member.email.eq(SecurityUtils.getCurrentMemberEmail()))
          .then(true)
          .otherwise(false).as("meYN"),
        Expressions.stringTemplate("concat({0}, {1})",
            Url.BAEKJOON_USER.getUri(), member.handle.stringValue())
          .as("baekjoonUrl"))
      )
      .from(member)
      .where(member.handle.eq(handle))
      .fetchOne();
  }

  /**
   * 내 정보 조회
   */
  public MemberInfoResponse getMyInfo() {
    return queryFactory
      .select(Projections.fields(
          MemberInfoResponse.class,
          member.id.as("memberId"),
          member.profileUrl,
          member.name,
          member.email,
          member.grade,
          member.major,
          member.handle,
          member.phoneNumber,
          member.role,
          Expressions.as(JPAExpressions
              .selectFrom(studyMember)
              .where(
                studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail()),
                studyMember.status.eq(StudyMemberStatus.PASS),
                studyMember.study.type.eq(StudyType.REGULAR)
              ).exists()
            , "regularStudyMemberYn")
        )
      )
      .from(member)
      .where(member.email.eq(SecurityUtils.getCurrentMemberEmail()))
      .fetchOne();
  }
}
