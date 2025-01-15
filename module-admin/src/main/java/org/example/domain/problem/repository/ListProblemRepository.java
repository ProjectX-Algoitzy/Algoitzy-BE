package org.example.domain.problem.repository;

import static org.example.domain.problem.QProblem.problem;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.problem.controller.request.SearchProblemRequest;
import org.example.domain.problem.controller.response.ListProblemDto;
import org.example.util.ArrayUtils;
import org.example.util.http_request.Url;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListProblemRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 백준 문제 목록 조회
   */
  public Page<ListProblemDto> getProblemList(SearchProblemRequest request) {
    List<ListProblemDto> problemList = queryFactory
      .select(Projections.fields(
        ListProblemDto.class,
        problem.number,
        problem.name,
        problem.level.stringValue().as("levelUrl"),
        Expressions.stringTemplate("concat({0}, {1})",
            Url.BAEKJOON_PROBLEM.getUri(), problem.number.stringValue())
          .as("baekjoonUrl"))
      )
      .from(problem)
      .where(searchKeyword(request.searchKeyword()))
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .orderBy(problem.solvedCount.desc())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(problem.count())
      .from(problem)
      .where(searchKeyword(request.searchKeyword()));

    return PageableExecutionUtils.getPage(problemList, request.pageRequest(), countQuery::fetchOne);

  }

  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }

    BooleanBuilder builder = new BooleanBuilder();
    if (ArrayUtils.isAllNumber(searchKeyword)) {
      builder.or(problem.number.eq(Integer.parseInt(searchKeyword)));
    }

    builder.or(problem.name.contains(searchKeyword));
    return builder;
  }
}
