package org.example.domain.study_member.repository;

import static org.example.domain.member.QMember.member;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.member.enums.Role;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.controller.response.ListTempStudyMemberDto;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

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
   * 자율 스터디원 목록 조회
   */
  public List<ListTempStudyMemberDto> getTempStudyMemberList(Long studyId) {
    return queryFactory
      .select(
        Projections.fields(
          ListTempStudyMemberDto.class,
          studyMember.id.as("studyMemberId"),
          studyMember.member.name,
          studyMember.member.phoneNumber,
          studyMember.role.as("memberRole"),
          studyMember.status
        )
      )
      .from(studyMember)
      .where(
        studyMember.study.id.eq(studyId)
      )
      .orderBy(
        new CaseBuilder()
          .when(studyMember.status.eq(StudyMemberStatus.PASS)).then(0)
          .otherwise(1).asc(),
        new CaseBuilder()
          .when(studyMember.role.eq(StudyMemberRole.LEADER)).then(0)
          .otherwise(1).asc(),
        studyMember.member.name.asc()
      )
      .fetch();
  }

  /**
   * 정규 스터디 스터디원 추가 가능 여부 반환
   */
  public boolean canAddRegularStudyMember() {
    return !queryFactory
      .selectFrom(member)
      .where(
        member.role.eq(Role.ROLE_USER),
        member.blockYN.isFalse(),
        isRegularStudyMember()
      )
      .fetch()
      .isEmpty();
  }

  /**
   * 정규 스터디 참여 이력 있는 멤버
   */
  private Predicate isRegularStudyMember() {
    return member.eq(
      queryFactory
        .select(studyMember.member)
        .from(studyMember)
        .where(
          studyMember.study.type.eq(StudyType.REGULAR),
          studyMember.status.eq(StudyMemberStatus.PASS),
          studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail())
        )
        .groupBy(member)
    );
  }

}
