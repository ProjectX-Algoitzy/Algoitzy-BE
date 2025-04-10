package org.example.domain.inquiry.repository;


import static org.example.domain.inquiry.QInquiry.inquiry;
import static org.example.domain.inquiry_reply.QInquiryReply.inquiryReply;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailInquiryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 문의 상세 조회
   */
  public DetailInquiryResponse getInquiry(Long inquiryId) {
    return queryFactory.select(Projections.fields(
        DetailInquiryResponse.class,
        inquiry.id.as("inquiryId"),
        inquiry.category.stringValue().as("categoryCode"),
        inquiry.category.stringValue().as("categoryName"),
        inquiry.title,
        inquiry.content,
        inquiry.member.name.as("createdName"),
        inquiry.member.handle,
        inquiry.member.profileUrl,
        inquiry.createdTime,
        inquiry.viewCount,
        inquiry.member.email.eq(SecurityUtils.getCurrentMemberEmail()).as("myInquiryYn"),
//        inquiry.replyList.size().as("replyCount"),
        Expressions.as(
          JPAExpressions
            .select(inquiryReply.count())
            .from(inquiryReply)
            .where(
              inquiryReply.inquiry.eq(inquiry),
              inquiryReply.deleteYn.isFalse())
        , "replyCount"),
        inquiry.publicYn,
        inquiry.solvedYn
      ))
      .from(inquiry)
      .where(inquiry.id.eq(inquiryId))
      .fetchOne();
  }

}
