package org.example.domain.attendance.repository;

import static org.example.domain.attendance.QAttendance.attendance;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance.enums.AttendanceType;
import org.example.domain.challenge_reward.controller.request.UseChallengeRewardDto;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailAttendanceRepository {

  private final JPAQueryFactory queryFactory;

  public Attendance getAttendanceForReward(UseChallengeRewardDto request) {
    return queryFactory
      .selectFrom(attendance)
      .innerJoin(studyMember).on(attendance.studyMember.eq(studyMember))
      .innerJoin(member).on(studyMember.member.eq(member))
      .innerJoin(study).on(studyMember.study.eq(study))
      .where(
        attendance.id.eq(request.attendanceId()),
        member.email.eq(SecurityUtils.getCurrentMemberEmail()),
        study.endYN.isFalse(),
        attendanceTypeEq(request.attendanceType())
      )
      .fetchOne();
  }

  private Predicate attendanceTypeEq(AttendanceType attendanceType) {
    BooleanBuilder builder = new BooleanBuilder();
    return switch (attendanceType) {
      case PROBLEM -> builder.or(attendance.problemYN.isFalse());
      case BLOG -> builder.or(attendance.blogYN.isFalse());
      case WORKBOOK -> builder.or(attendance.workbookYN.isFalse());
    };
  }
}
