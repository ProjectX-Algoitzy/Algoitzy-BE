package org.example.domain.study.repository;

import static org.example.domain.study.QStudy.study;

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
    return queryFactory
      .selectFrom(study)
      .where(
        study.generation.eq(getMaxStudyGeneration())
      )
      .fetch().size();
  }

  /**
   * 스터디 최신 기수
   */
  public Integer getMaxStudyGeneration() {
    return queryFactory
      .select(study.generation.max())
      .from(study)
      .fetchOne();
  }
}
