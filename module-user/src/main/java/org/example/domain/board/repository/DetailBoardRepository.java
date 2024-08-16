package org.example.domain.board.repository;

import static org.example.domain.Board.QBoard.board;
import static org.example.domain.board_file.QBoardFile.boardFile;
import static org.example.domain.member.QMember.member;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailBoardRepository {

  private final JPAQueryFactory queryFactory;

  public DetailBoardResponse getBoard(String boardId) {
    return queryFactory
      .select(Projections.fields(
          DetailBoardResponse.class,
          board.title,
          board.content,
          Expressions.as(
            JPAExpressions
              .select(member.name)
              .from(member)
              .where(member.email.eq(board.updatedBy)
              )
            , "createdName"),
          board.updatedTime
        )
      )
      .from(board)
      .innerJoin(boardFile).on(board.eq(boardFile.board))
      .where(board.id.eq(UUID.fromString(boardId)))
      .fetchOne();
  }
}
