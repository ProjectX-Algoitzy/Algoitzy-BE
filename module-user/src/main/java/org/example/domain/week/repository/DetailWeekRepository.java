package org.example.domain.week.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.week.Week;
import org.example.domain.week.controller.response.DetailWeekResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailWeekRepository {

  private final JPAQueryFactory queryFactory;


  public Optional<Week> getCurrentWeek() {
    return Optional.ofNullable(queryFactory
      .selectFrom(week)
      .where(
        week.startTime.loe(LocalDateTime.now()),
        week.endTime.goe(LocalDateTime.now())
      )
      .fetchOne()
    );
  }

  /**
   * 현재 주차 정보 조회
   */
  public DetailWeekResponse getWeek() {
    return queryFactory
      .select(Projections.fields(
          DetailWeekResponse.class,
          Expressions.as(
            JPAExpressions
              .select(
                generation.value
              )
              .from(generation)
              .where(week.generation.eq(generation))
            , "generation"),
          week.value.as("week"),
          week.startTime,
          week.endTime
        )
      )
      .from(week)
      .where(
        week.startTime.loe(LocalDateTime.now()),
        week.endTime.goe(LocalDateTime.now())
      )
      .fetchOne();
  }
}
