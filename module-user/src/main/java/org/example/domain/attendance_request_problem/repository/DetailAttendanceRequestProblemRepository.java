package org.example.domain.attendance_request_problem.repository;

import static org.example.domain.attendance_request.QAttendanceRequest.attendanceRequest;
import static org.example.domain.attendance_request_problem.QAttendanceRequestProblem.attendanceRequestProblem;
import static org.example.domain.generation.QGeneration.generation;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance_request_problem.AttendanceRequestProblem;
import org.example.domain.study_member.StudyMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailAttendanceRequestProblemRepository {

  private final JPAQueryFactory queryFactory;

  public Optional<AttendanceRequestProblem> findByProblemUrl(StudyMember studyMember, String problemUrl) {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return Optional.ofNullable(
      queryFactory
        .selectFrom(attendanceRequestProblem)
        .innerJoin(attendanceRequest).on(attendanceRequestProblem.attendanceRequest.eq(attendanceRequest))
        .where(
          attendanceRequest.week.generation.value.eq(maxGeneration),
          attendanceRequest.studyMember.eq(studyMember),
          attendanceRequestProblem.problemUrl.eq(problemUrl)
          )
        .fetchOne()
    );
  }
}
