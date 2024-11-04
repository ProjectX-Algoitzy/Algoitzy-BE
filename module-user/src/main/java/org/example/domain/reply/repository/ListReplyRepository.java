package org.example.domain.reply.repository;

import static org.example.domain.reply.QReply.reply;
import static org.example.domain.reply_like.QReplyLike.replyLike;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
            selectFields()
                .from(reply)
                .leftJoin(replyLike).on(reply.eq(replyLike.reply))
                .where(
                    reply.board.id.eq(boardId),
                    reply.parentId.isNull()
                )
                .offset(request.pageRequest().getOffset())
                .limit(request.pageRequest().getPageSize())
                .orderBy(reply.createdTime.desc())
                .fetch();

        return PageableExecutionUtils.getPage(boardList, request.pageRequest(), () -> 0L);
    }

    public List<ListReplyDto> getChildrenReplyList(Long boardId) {
        return selectFields()
            .from(reply)
            .leftJoin(replyLike).on(reply.eq(replyLike.reply))
            .where(
                reply.board.id.eq(boardId),
                reply.parentId.isNotNull()
            )
            .orderBy(reply.createdTime.asc())
            .fetch();
    }

    private JPAQuery<ListReplyDto> selectFields() {
        return queryFactory.select(Projections.fields(ListReplyDto.class,
            reply.id.as("replyId"),
            reply.id.as("parentReplyId"),
            reply.member.name.as("createdName"),
            reply.member.profileUrl,
            reply.content,
            reply.createdTime,
            reply.depth,
            Expressions.as(
                JPAExpressions
                    .selectFrom(replyLike)
                    .where(
                        replyLike.reply.eq(reply),
                        replyLike.member.email.eq(SecurityUtils.getCurrentMemberEmail())
                    ).exists(),
                "myLikeYn"
            )
        ));
    }


}

