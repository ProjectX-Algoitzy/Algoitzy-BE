package org.example.domain.attendance.repository;

import static org.example.domain.attendance.QAttendance.attendance;
import static org.example.domain.study_member.QStudyMember.studyMember;

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
          attendance.week.value.as("week"),
          studyMember.member.name,
          attendance.problemYN,
          attendance.blogYN,
          attendance.workbookYN
        )
      )
      .from(attendance)
      .innerJoin(studyMember).on(attendance.studyMember.eq(studyMember))
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
