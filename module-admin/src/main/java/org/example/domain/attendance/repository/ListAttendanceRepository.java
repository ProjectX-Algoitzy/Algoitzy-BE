package org.example.domain.attendance.repository;

import static org.example.domain.attendance.QAttendance.attendance;
import static org.example.domain.member.QMember.member;
import static org.example.domain.study_member.QStudyMember.studyMember;
import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAttendanceRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListAttendanceDto> getAttendanceList(Long studyId) {
    return queryFactory
      .select(
        Projections.fields(
          ListAttendanceDto.class,
          attendance.id.as("attendanceId"),
          attendance.week.value.as("week"),
          studyMember.member.name,
          studyMember.member.handle,
          attendance.problemYN,
          attendance.blogYN,
          attendance.workbookYN
        )
      )
      .from(attendance)
      .rightJoin(studyMember).on(attendance.studyMember.eq(studyMember))
      .innerJoin(member).on(studyMember.member.eq(member))
      .leftJoin(week).on(attendance.week.eq(week))
      .where(
        studyMember.study.id.eq(studyId),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .orderBy(
        attendance.week.value.asc(),
        studyMember.member.name.asc()
      )
      .fetch();
  }
}
