package org.example.domain.study.repository;

import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
        study.name
        )
      )
      .from(study)
      .where(study.type.eq(StudyType.REGULAR))
      .fetch();
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
