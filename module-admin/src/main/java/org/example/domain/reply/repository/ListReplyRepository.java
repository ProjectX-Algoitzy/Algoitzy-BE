package org.example.domain.reply.repository;

import static org.example.domain.board.QBoard.board;
import static org.example.domain.reply.QReply.reply;
import static org.example.domain.reply_like.QReplyLike.replyLike;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.reply.Reply;
import org.example.domain.reply.controller.request.SearchReplyRequest;
import org.example.domain.reply.controller.response.ListReplyDto;
import org.example.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListReplyRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ListReplyDto> getParentReplyList(Long boardId, SearchReplyRequest request) {
    List<ListReplyDto> boardList =
      selectFields(boardId)
        .from(reply)
        .leftJoin(replyLike).on(reply.eq(replyLike.reply))
        .where(
          reply.board.id.eq(boardId),
          reply.parentId.isNull()
        )
        .groupBy(reply)
        .offset(request.pageRequest().getOffset())
        .limit(request.pageRequest().getPageSize())
        .orderBy(reply.createdTime.desc())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(reply.count())
      .from(reply)
      .where(
        reply.board.id.eq(boardId),
        reply.parentId.isNull()
      );

    return PageableExecutionUtils.getPage(boardList, request.pageRequest(), countQuery::fetchOne);
  }

  public List<ListReplyDto> getChildrenReplyList(Long boardId) {
    return selectFields(boardId)
      .from(reply)
      .leftJoin(replyLike).on(reply.eq(replyLike.reply))
      .where(
        reply.board.id.eq(boardId),
        reply.parentId.isNotNull()
      )
      .groupBy(reply)
      .orderBy(reply.createdTime.asc())
      .fetch();
  }

  private JPAQuery<ListReplyDto> selectFields(Long boardId) {
    return queryFactory
      .select(Projections.fields(
          ListReplyDto.class,
          reply.parentId.as("parentReplyId"),
          reply.id.as("replyId"),
          reply.member.handle.as("handle"),
          reply.member.name.as("createdName"),
          reply.member.profileUrl,
          reply.content,
          reply.createdTime,
          reply.member.eq(
            JPAExpressions
              .select(board.member)
              .from(board)
              .where(board.id.eq(boardId))
          ).as("myBoardYn"),
          Expressions.as(
            JPAExpressions
              .selectFrom(replyLike)
              .where(
                replyLike.reply.eq(reply),
                replyLike.member.email.eq(SecurityUtils.getCurrentMemberEmail())
              ).exists()
            , "myLikeYn"),
          reply.replyLikeList.size().as("likeCount"),
          reply.depth,
          reply.deleteYn,
          reply.deleteByAdminYn
        )
      );
  }

  public List<Reply> getChildrenList(Long parentId) {
    return queryFactory
      .selectFrom(reply)
      .where(
        (parentId == null) ? reply.parentId.isNull() : reply.parentId.eq(parentId)
      )
      .orderBy(reply.deleteYn.asc())
      .fetch();
  }

}
