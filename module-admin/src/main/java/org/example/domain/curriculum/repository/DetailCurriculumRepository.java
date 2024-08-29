package org.example.domain.curriculum.repository;

import static org.example.domain.curriculum.QCurriculum.curriculum;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailCurriculumRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 커리큘럼 상세 조회
   */
  public DetailCurriculumResponse getCurriculum(Long curriculumId) {
    return queryFactory
      .select(Projections.fields(DetailCurriculumResponse.class,
        curriculum.title,
        curriculum.week,
        curriculum.content,
        study.id.as("studyId"),
        study.name.as("studyName")
        )
      )
      .from(curriculum)
      .innerJoin(study).on(curriculum.study.eq(study))
      .where(curriculum.id.eq(curriculumId))
      .fetchOne();
  }
}
