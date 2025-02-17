package org.example.domain.workbook.repository;

import static org.example.domain.workbook.QWorkbook.workbook;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.Study;
import org.example.domain.workbook.controller.response.ListInstitutionWorkbookDto;
import org.example.domain.workbook.controller.response.ListWorkbookDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListWorkbookRepository {

  private final JPAQueryFactory queryFactory;


  public List<ListWorkbookDto> getWorkbookList(Study study) {
    return queryFactory
      .select(Projections.fields(
          ListWorkbookDto.class,
          workbook.id.as("workbookId"),
          workbook.week.value.as("week")
        )
      )
      .from(workbook)
      .where(workbook.study.eq(study))
      .orderBy(workbook.week.value.asc())
      .fetch();
  }

  /**
   * 기관 문제집 목록 조회
   */
  public List<ListInstitutionWorkbookDto> getInstitutionWorkbookList(Long institutionId) {
    return queryFactory
      .select(Projections.fields(
          ListInstitutionWorkbookDto.class,
          workbook.id.as("workbookId"),
          workbook.name
        )
      )
      .from(workbook)
      .where(workbook.institution.id.eq(institutionId))
      .orderBy(workbook.name.asc())
      .fetch();
  }
}
