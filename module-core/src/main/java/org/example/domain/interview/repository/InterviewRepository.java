package org.example.domain.interview.repository;

import org.example.domain.interview.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

}
