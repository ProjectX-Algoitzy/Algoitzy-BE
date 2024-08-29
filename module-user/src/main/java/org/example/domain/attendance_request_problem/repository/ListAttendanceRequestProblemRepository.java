package org.example.domain.attendance_request_problem.repository;

import static org.example.domain.attendance_request_problem.QAttendanceRequestProblem.attendanceRequestProblem;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListAttendanceRequestProblemRepository {

  private final JPAQueryFactory queryFactory;


  public List<String> getAttendanceRequestProblemList(Long attendanceRequestId) {
    return queryFactory
      .select(attendanceRequestProblem.problemUrl)
      .from(attendanceRequestProblem)
      .where(attendanceRequestProblem.attendanceRequest.id.eq(attendanceRequestId))
      .fetch();
  }
}
