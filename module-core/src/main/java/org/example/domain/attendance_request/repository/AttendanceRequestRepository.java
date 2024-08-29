package org.example.domain.attendance_request.repository;

import java.util.Optional;
import org.example.domain.attendance_request.AttendanceRequest;
import org.example.domain.study_member.StudyMember;
import org.example.domain.week.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRequestRepository extends JpaRepository<AttendanceRequest, Long> {

  Optional<AttendanceRequest> findByBlogUrl(String blogUrl);

  Optional<AttendanceRequest> findByWeekAndStudyMember(Week week, StudyMember studyMember);
}
