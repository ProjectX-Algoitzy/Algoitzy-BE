package org.example.domain.application.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.response.ListApplicationByGenerationDto;
import org.example.domain.member.QMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListApplicationRepository {

  private final JPAQueryFactory queryFactory;
  private final QMember create = new QMember("create");
  private final QMember update = new QMember("update");


  /**
   * 기수별 지원서 양식 목록 조회
   */
  public List<ListApplicationByGenerationDto> getApplicationList(int generation) {
    return queryFactory
      .select(Projections.fields(ListApplicationByGenerationDto.class,
          application.id.as("applicationId"),
          application.title,
          study.name.as("studyName"),
          create.name.as("createdName"),
          application.createdTime,
          update.name.as("updatedName"),
          application.updatedTime
        )
      )
      .from(application)
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(create).on(application.createdBy.eq(create.email))
      .innerJoin(update).on(application.updatedBy.eq(update.email))
      .where(
        study.generation.eq(generation)
      )
      .orderBy(application.updatedTime.desc())
      .fetch();
  }

  public int getMaxStudyGeneration() {
    return Objects.requireNonNull(queryFactory
      .select(study.generation.max())
      .from(study)
      .fetchOne()
    );
  }
}
