package org.example.domain.inquiry.repository;


import static org.example.domain.inquiry.QInquiry.inquiry;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry.controller.response.DetailInquiryResponse;
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
        inquiry.member.profileUrl,
        inquiry.createdTime,
        inquiry.viewCount,
        inquiry.replyList.size().as("replyCount"),
        inquiry.publicYn
      ))
      .from(inquiry)
      .where(inquiry.id.eq(inquiryId))
      .fetchOne();
  }

}
