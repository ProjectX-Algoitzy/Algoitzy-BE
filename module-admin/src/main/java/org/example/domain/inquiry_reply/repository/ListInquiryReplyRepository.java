package org.example.domain.inquiry_reply.repository;

import static org.example.domain.inquiry.QInquiry.inquiry;
import static org.example.domain.inquiry_reply.QInquiryReply.inquiryReply;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry_reply.InquiryReply;
import org.example.domain.inquiry_reply.controller.request.SearchInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.response.ListInquiryReplyDto;
import org.example.util.SecurityUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListInquiryReplyRepository {

  private final JPAQueryFactory queryFactory;


  public Page<ListInquiryReplyDto> getParentReplyList(Long inquiryId, SearchInquiryReplyRequest request) {
    List<ListInquiryReplyDto> inquiryList =
      selectFields(inquiryId)
        .from(inquiryReply)
        .where(
          inquiryReply.inquiry.id.eq(inquiryId),
          inquiryReply.parentId.isNull()
        )
        .offset(request.pageRequest().getOffset())
        .limit(request.pageRequest().getPageSize())
        .orderBy(inquiryReply.createdTime.desc())
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(inquiryReply.count())
      .from(inquiryReply)
      .where(
        inquiryReply.inquiry.id.eq(inquiryId),
        inquiryReply.parentId.isNull()
      );

    return PageableExecutionUtils.getPage(inquiryList, request.pageRequest(), countQuery::fetchOne);
  }

  public List<ListInquiryReplyDto> getChildrenReplyList(Long inquiryId) {
    return selectFields(inquiryId)
      .from(inquiryReply)
      .where(
        inquiryReply.inquiry.id.eq(inquiryId),
        inquiryReply.parentId.isNotNull()
      )
      .groupBy(inquiryReply)
      .orderBy(inquiryReply.createdTime.asc())
      .fetch();
  }

  private JPAQuery<ListInquiryReplyDto> selectFields(Long inquiryId) {
    return queryFactory.select(
      Projections.fields(
        ListInquiryReplyDto.class,
        inquiryReply.id.as("replyId"),
        inquiryReply.parentId.as("parentReplyId"),
        inquiryReply.member.role.as("createdRole"),
        inquiryReply.member.name.as("createdName"),
        inquiryReply.member.profileUrl,
        inquiryReply.content,
        inquiryReply.createdTime,
        inquiryReply.member.email.eq(SecurityUtils.getCurrentMemberEmail()).as("myReplyYn"),
        inquiryReply.member.eq(
          JPAExpressions
            .select(inquiry.member)
            .from(inquiry)
            .where(inquiry.id.eq(inquiryId))
        ).as("myInquiryYn"),
        inquiryReply.deleteYn
      ));
  }

  public List<InquiryReply> getChildrenList(Long parentId) {
    return queryFactory
      .selectFrom(inquiryReply)
      .where(
        (parentId == null) ? inquiryReply.parentId.isNull() : inquiryReply.parentId.eq(parentId)
      )
      .orderBy(inquiryReply.deleteYn.asc())
      .fetch();
  }
}

