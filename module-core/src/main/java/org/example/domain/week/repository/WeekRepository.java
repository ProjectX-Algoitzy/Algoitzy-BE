package org.example.domain.week.repository;

import org.example.domain.week.Week;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekRepository extends JpaRepository<Week, Long> {

  Week findTopByOrderByEndTimeDesc();
}
