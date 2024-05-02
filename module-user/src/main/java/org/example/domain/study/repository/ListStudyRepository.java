package org.example.domain.study.repository;

import static org.example.domain.study.QStudy.study;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyRepository {

  private final JPAQueryFactory queryFactory;


  /**
   * 최신 기수 스터디 개수
   */
  public Integer getStudyCount() {
    JPAQuery<Integer> subQuery = queryFactory
      .select(study.generation.max())
      .from(study);

    return queryFactory
      .selectFrom(study)
      .where(
        study.generation.eq(subQuery)
      )
      .fetch().size();
  }
}
