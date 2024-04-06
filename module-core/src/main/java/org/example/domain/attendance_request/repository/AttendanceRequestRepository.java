package org.example.domain.attendance_request.repository;

import org.example.domain.attendance_request.AttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRequestRepository extends JpaRepository<AttendanceRequest, Long> {

}
