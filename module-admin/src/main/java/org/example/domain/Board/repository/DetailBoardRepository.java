package org.example.domain.Board.repository;

import static org.example.domain.board.QBoard.board;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.Board.controller.response.DetailBoardResponse;
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
          board.category.stringValue().as("category"),
          board.title,
          board.member.id.as("createMemberId"),
          board.member.name.as("createdName"),
          board.createdTime,
          board.viewCount,
          board.id.as("boardId"),
          board.content,
          board.boardLikeList.size().as("likeCount"),
          board.replyList.size().as("replyCount"),
          board.fixYn
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
}
