package org.example.domain.study.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.Study;
import org.example.domain.study.controller.response.ListRegularStudyDto;
import org.example.domain.study.enums.StudyType;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 커리큘럼 목록 조회(드롭박스용)
   */
  public List<ListRegularStudyDto> getRegularStudyList() {
    return queryFactory
      .select(Projections.fields(ListRegularStudyDto.class,
          study.id.as("studyId"),
          study.profileUrl,
          study.name
        )
      )
      .from(study)
      .where(study.type.eq(StudyType.REGULAR))
      .fetch();
  }

  public List<Study> getOldGenerationStudyList(StudyType type) {
    Integer oldGeneration =
      Objects.requireNonNull(
        queryFactory
          .select(generation.value.max())
          .from(generation)
          .fetchOne()) - 1;

    return queryFactory
      .selectFrom(study)
      .where(
        study.type.eq(type),
        study.generation.value.eq(oldGeneration)
      )
      .fetch();
  }
}
