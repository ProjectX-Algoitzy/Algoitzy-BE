package org.example.domain.problem.repository;

import static org.example.domain.problem.QProblem.problem;
import static org.example.domain.problem_algorithm.QProblemAlgorithm.problemAlgorithm;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.problem.Level;
import org.example.domain.problem.Problem;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListProblemRepository {

  private final JPAQueryFactory queryFactory;


  public List<Problem> getProblemList(List<String> algorithmList) {
    return queryFactory
      .selectFrom(problem)
      .innerJoin(problemAlgorithm).on(problem.eq(problemAlgorithm.problem))
      .where(
        problemAlgorithm.algorithm.englishName.in(algorithmList),
        problem.level.in(Level.findByLevel(6, 13)) // SILVER5 ~ GOLD3
      )
      .groupBy(problem.number)
      .orderBy(problem.solvedCount.desc())
      .limit(5)
      .fetch();
  }
}
