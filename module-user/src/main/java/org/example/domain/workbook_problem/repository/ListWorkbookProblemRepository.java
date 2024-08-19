package org.example.domain.workbook_problem.repository;

import static org.example.domain.problem.QProblem.problem;
import static org.example.domain.workbook_problem.QWorkbookProblem.workbookProblem;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.workbook_problem.controller.response.ListWorkbookProblemDto;
import org.example.util.http_request.Url;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListWorkbookProblemRepository {

  private final JPAQueryFactory queryFactory;


  public List<ListWorkbookProblemDto> getWorkbookProblemList(Long workbookId) {
    return queryFactory
      .select(Projections.fields(
          ListWorkbookProblemDto.class,
          workbookProblem.problem.number,
          workbookProblem.problem.name,
          workbookProblem.problem.level.stringValue().as("levelUrl"),
          Expressions.stringTemplate("concat({0}, {1})",
              Url.BAEKJOON_PROBLEM.getUri(), problem.number.stringValue())
            .as("baekjoonUrl")
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
