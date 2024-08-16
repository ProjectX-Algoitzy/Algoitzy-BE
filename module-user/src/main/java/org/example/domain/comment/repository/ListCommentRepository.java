package org.example.domain.comment.repository;

import static org.example.domain.Board.QBoard.board;
import static org.example.domain.comment.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.comment.controller.request.SearchCommentRequest;
import org.example.domain.comment.controller.response.ListCommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListCommentRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 댓글 목록 조회
   */
  public Page<ListCommentDto> getCommentList(Long boardId, SearchCommentRequest request) {
    List<ListCommentDto> commentList = queryFactory
      .select(
        Projections.fields(
          ListCommentDto.class,
          comment.id.as("commentId"),
          comment.createdBy.as("createdName"),
          comment.content
        )
      )
      .from(comment)
      .innerJoin(board).on(comment.board.eq(board))
      .where(
        board.id.eq(boardId)
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .orderBy(
        board.updatedTime.desc()
      )
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(comment.count())
      .from(comment)
      .innerJoin(board).on(comment.board.eq(board))
      .where(board.id.eq(boardId));

    return PageableExecutionUtils.getPage(commentList, request.pageRequest(), countQuery::fetchOne);
  }
}
