package org.example.domain.board.repository;

import static org.example.domain.board.QBoard.board;
import static org.example.domain.board_like.QBoardLike.boardLike;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.domain.board.controller.response.DetailDraftBoardResponse;
import org.example.domain.board.enums.BoardCategory;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailBoardRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 게시글 상세 조회
   */
  public DetailBoardResponse getBoard(Long boardId) {
    return queryFactory
      .select(
        Projections.fields(
          DetailBoardResponse.class,
          board.category.stringValue().as("categoryCode"),
          board.category.stringValue().as("category"),
          board.title,
          board.member.id.as("createMemberId"),
          board.member.profileUrl,
          board.member.name.as("createdName"),
          board.createdTime,
          board.viewCount,
          board.id.as("boardId"),
          board.content,
          board.boardLikeList.size().as("likeCount"),
          Expressions.as(
            JPAExpressions
              .selectFrom(boardLike)
              .where(
                boardLike.board.eq(board),
                boardLike.member.email.eq(SecurityUtils.getCurrentMemberEmail())
              ).exists()
            , "myLikeYn"),
          board.replyList.size().as("replyCount"),
          board.saveYn,
          board.fixYn,
          board.deleteYn
        )
      )
      .from(board)
      .where(
        board.id.eq(boardId),
        board.saveYn.isTrue()
      )
      .groupBy(board)
      .fetchOne();
  }

  /**
   * 임시저장 게시글 상세 조회
   */
  public DetailDraftBoardResponse getDraftBoard(Long boardId) {
    return queryFactory
      .select(
        Projections.fields(
          DetailDraftBoardResponse.class,
          board.category.stringValue().as("categoryCode"),
          board.category.stringValue().as("category"),
          board.title,
          board.id.as("boardId"),
          board.content,
          board.saveYn
        )
      )
      .from(board)
      .where(
        board.id.eq(boardId),
        board.saveYn.isFalse(),
        board.category.eq(BoardCategory.NOTICE)
      )
      .groupBy(board)
      .fetchOne();
  }

}
