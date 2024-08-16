package org.example.domain.board.repository;

import static org.example.domain.Board.QBoard.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.request.SearchBoardRequest;
import org.example.domain.board.controller.response.ListBoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListBoardRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ListBoardDto> getBoardList(SearchBoardRequest request) {
    List<ListBoardDto> boardList = queryFactory
      .select(
        Projections.fields(
          ListBoardDto.class,
          board.id.as("boardId"),
          board.title,
          board.updatedTime
        )
      )
      .from(board)
      .where(
        searchKeyword(request.searchKeyword())
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .orderBy(
        board.updatedTime.desc()
      )
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(board.count())
      .from(board)
      .where(
        searchKeyword(request.searchKeyword())
      );

    return PageableExecutionUtils.getPage(boardList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 키워드 검색(제목)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(board.title.contains(searchKeyword));
    return builder;
  }
}
