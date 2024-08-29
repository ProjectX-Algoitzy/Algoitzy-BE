package org.example.domain.attendance.repository;

import org.example.domain.attendance.Attendance;
import org.example.domain.week.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  boolean existsAllByWeek(Week week);
}
