package org.example.domain.problem_algorithm.repository;

import java.util.List;
import org.example.domain.problem.Problem;
import org.example.domain.problem_algorithm.ProblemAlgorithm;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProblemAlgorithmRepository extends JpaRepository<ProblemAlgorithm, Long> {

  @Modifying
  @Query("delete from ProblemAlgorithm pa")
  void deleteAll();

  @EntityGraph(attributePaths = {"algorithm"})
  List<ProblemAlgorithm> findAllByProblem(Problem problem);
}
