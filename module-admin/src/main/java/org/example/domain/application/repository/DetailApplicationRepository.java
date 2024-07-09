package org.example.domain.application.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.response.DetailApplicationResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailApplicationRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 지원서 양식 상세 조회
   */
  public DetailApplicationResponse getApplication(Long applicationId) {
    return queryFactory
      .select(Projections.fields(DetailApplicationResponse.class,
        application.title,
        study.generation.value.as("generation"),
        study.id.as("studyId"),
        study.name.as("studyName"),
        application.confirmYN
        )
      )
      .from(application)
      .leftJoin(study).on(application.study.eq(study))
      .where(
        application.id.eq(applicationId)
      )
      .fetchOne();
  }
}
