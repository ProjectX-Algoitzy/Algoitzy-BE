package org.example.domain.problem.repository;

import org.example.domain.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    long count();
}
