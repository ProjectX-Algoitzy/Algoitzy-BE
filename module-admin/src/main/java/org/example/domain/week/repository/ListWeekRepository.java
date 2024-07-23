package org.example.domain.week.repository;

import static org.example.domain.week.QWeek.week;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.generation.Generation;
import org.example.domain.week.controller.response.ListWeekDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListWeekRepository {

  private final JPAQueryFactory queryFactory;


  public List<ListWeekDto> getWeekList(Generation generation) {
    return queryFactory
      .select(Projections.fields(
          ListWeekDto.class,
          week.value.as("week"),
          week.startTime,
          week.endTime
        )
      )
      .from(week)
      .where(week.generation.eq(generation))
      .orderBy(week.value.asc())
      .fetch();
  }
}
