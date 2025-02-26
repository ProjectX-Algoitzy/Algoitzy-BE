package org.example.domain.attendance_request_problem.repository;

import static org.example.domain.attendance_request_problem.QAttendanceRequestProblem.attendanceRequestProblem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request.AttendanceRequest;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAttendanceRequestProblemRepository {

  private final JPAQueryFactory queryFactory;

  public int getRequestCount(AttendanceRequest attendanceRequest) {
    return queryFactory
      .select(attendanceRequestProblem)
      .from(attendanceRequestProblem)
      .where(
        attendanceRequestProblem.attendanceRequest.eq(attendanceRequest),
        attendanceRequestProblem.problemUrl.contains("acmicpc.net").not(), // 백준 제외
        attendanceRequestProblem.problemUrl.contains("boj.kr").not() // 백준 제외
      )
      .fetch().size();
  }

  public List<String> getAttendanceRequestProblemList(Long attendanceRequestId) {
    return queryFactory
      .select(attendanceRequestProblem.problemUrl)
      .from(attendanceRequestProblem)
      .where(attendanceRequestProblem.attendanceRequest.id.eq(attendanceRequestId))
      .fetch();
  }
}
