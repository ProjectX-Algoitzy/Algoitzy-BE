package org.example.domain.week.repository;

import static org.example.domain.week.QWeek.week;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.domain.week.Week;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailWeekRepository {

  private final JPAQueryFactory queryFactory;

  public Optional<Week> getLastWeek() {
    LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);
    return Optional.ofNullable(
      queryFactory
        .selectFrom(week)
        .where(
          week.startTime.loe(lastWeek),
          week.endTime.goe(lastWeek)
        )
        .fetchOne()
    );
  }
}
