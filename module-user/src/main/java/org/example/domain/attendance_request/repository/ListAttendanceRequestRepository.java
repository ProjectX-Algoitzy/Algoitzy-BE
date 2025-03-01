package org.example.domain.attendance_request.repository;

import static org.example.domain.attendance_request.QAttendanceRequest.attendanceRequest;
import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.controller.response.ListAttendanceRequestDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAttendanceRequestRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 현재 주차 출석 요청 조회
   */
  public List<ListAttendanceRequestDto> getAttendanceRequestList(Long studyId, String handle) {
    return queryFactory
      .select(
        Projections.fields(
          ListAttendanceRequestDto.class,
          attendanceRequest.week.value.as("week"),
          attendanceRequest.id.as("attendanceRequestId"),
          attendanceRequest.blogUrl
        )
      )
      .from(attendanceRequest)
      .innerJoin(week).on(attendanceRequest.week.eq(week))
      .where(
        attendanceRequest.studyMember.study.id.eq(studyId),
        attendanceRequest.studyMember.member.handle.eq(handle)
      )
      .orderBy(attendanceRequest.week.value.asc())
      .fetch();
  }
}
