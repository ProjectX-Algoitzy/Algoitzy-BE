package org.example.domain.challenge_problem.repository;

import java.time.LocalDate;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeProblemRepository extends JpaRepository<ChallengeProblem, LocalDate> {

}
