package org.example.domain.attendance_request.repository;

import static org.example.domain.attendance_request.QAttendanceRequest.attendanceRequest;
import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.response.DetailAttendanceRequestResponse;
import org.example.domain.study_member.StudyMember;
import org.example.domain.week.Week;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailAttendanceRequestRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 현재 주차 출석 요청 조회
   */
  public DetailAttendanceRequestResponse getAttendanceRequest(Week currentWeek, StudyMember studyMember) {
    return Optional.ofNullable(queryFactory
        .select(Projections.fields(
            DetailAttendanceRequestResponse.class,
            attendanceRequest.id.as("attendanceRequestId"),
            attendanceRequest.blogUrl
          )
        )
        .from(attendanceRequest)
        .innerJoin(week).on(attendanceRequest.week.eq(week))
        .where(
          week.eq(currentWeek),
          attendanceRequest.studyMember.eq(studyMember)
        )
        .fetchOne())
      .orElse(DetailAttendanceRequestResponse.builder()
        .problemUrlList(Collections.emptyList())
        .blogUrl(null)
        .build());
  }
}
