package org.example.domain.curriculum.repository;

import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.curriculum.Curriculum;
import org.example.domain.curriculum.QCurriculum;
import org.example.domain.curriculum.controller.response.DetailCurriculumResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailCurriculumRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 커리큘럼 상세 조회
   */
  public DetailCurriculumResponse getCurriculum(Curriculum curriculum) {
    return queryFactory
      .select(Projections.fields(
          DetailCurriculumResponse.class,
          QCurriculum.curriculum.title,
          QCurriculum.curriculum.week,
          QCurriculum.curriculum.content,
          study.id.as("studyId"),
          study.name.as("studyName")
        )
      )
      .from(QCurriculum.curriculum)
      .innerJoin(study).on(QCurriculum.curriculum.study.eq(study))
      .where(QCurriculum.curriculum.eq(curriculum))
      .fetchOne();
  }
}
