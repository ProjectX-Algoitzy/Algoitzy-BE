package org.example.domain.board.repository;

import static org.example.domain.board.QBoard.board;
import static org.example.domain.board_like.QBoardLike.boardLike;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.board.controller.response.DetailBoardResponse;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailBoardRepository {

    private final JPAQueryFactory queryFactory;

    public DetailBoardResponse getDetailBoard(Long boardId) {
        return queryFactory.select(Projections.fields(
            DetailBoardResponse.class,
            board.id.as("boardId"),
            board.category.stringValue().as("category"),
            board.title,
            board.content,
            board.member.id.as("createMemberId"),
            board.member.name.as("createdName"),
            board.member.handle,
            board.member.profileUrl,
            board.createdTime,
            board.viewCount,
            board.saveYn,
            board.deleteYn,
            board.fixYn,
            board.replyList.size().as("replyCount"),
            board.boardLikeList.size().as("likeCount"),
            Expressions.as(
                JPAExpressions
                    .selectFrom(boardLike)
                    .where(
                        boardLike.board.eq(board),
                        boardLike.member.email.eq(SecurityUtils.getCurrentMemberEmail())
                ).exists(), "myLikeYn")
            ))
            .from(board)
            .where(
                board.id.eq(boardId),
                board.saveYn.isTrue()
            )
            .groupBy(board)
            .fetchOne();
    }
}
