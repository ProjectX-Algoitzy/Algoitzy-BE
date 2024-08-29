package org.example.domain.problem_algorithm.repository;

import org.example.domain.problem_algorithm.ProblemAlgorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProblemAlgorithmRepository extends JpaRepository<ProblemAlgorithm, Long> {

  @Modifying
  @Query("delete from ProblemAlgorithm pa")
  void deleteAll();
}
