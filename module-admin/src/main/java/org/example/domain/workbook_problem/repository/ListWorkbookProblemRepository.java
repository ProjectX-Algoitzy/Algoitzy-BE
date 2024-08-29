package org.example.domain.workbook_problem.repository;

import static org.example.domain.workbook_problem.QWorkbookProblem.workbookProblem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.Study;
import org.example.domain.week.Week;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListWorkbookProblemRepository {

  private final JPAQueryFactory queryFactory;


  public List<Integer> getWorkbookProblemList(Week week, Study study) {
    return queryFactory
      .select(workbookProblem.problem.number)
      .from(workbookProblem)
      .where(
        workbookProblem.workbook.week.eq(week),
        workbookProblem.workbook.study.eq(study)
      )
      .fetch();
  }

  public List<ListWorkbookProblemDto> getWorkbookProblemList(Long workbookId) {
    return queryFactory
      .select(Projections.fields(
          ListWorkbookProblemDto.class,
          workbookProblem.problem.number,
          workbookProblem.problem.name,
          workbookProblem.problem.level.stringValue().as("levelUrl")
        )
      )
      .from(workbookProblem)
      .where(
        workbookProblem.workbook.id.eq(workbookId)
      )
      .orderBy(workbookProblem.problem.level.asc())
      .fetch();
  }
}
