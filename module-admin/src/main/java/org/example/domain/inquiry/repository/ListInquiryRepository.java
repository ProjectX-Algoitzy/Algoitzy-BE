package org.example.domain.inquiry.repository;

import static org.example.domain.inquiry.QInquiry.inquiry;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry.controller.request.SearchInquiryRequest;
import org.example.domain.inquiry.controller.response.ListInquiryDto;
import org.example.domain.inquiry.enums.InquiryCategory;
import org.example.domain.inquiry.enums.InquirySort;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListInquiryRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 문의 목록 조회
   */
  public Page<ListInquiryDto> getInquiryList(SearchInquiryRequest request) {
    List<ListInquiryDto> inquiryList =
      selectFields()
        .from(inquiry)
        .where(
          searchCategory(request.category()),
          searchKeyword(request.searchKeyword())
        )
        .offset(request.pageRequest().getOffset())
        .limit(request.pageRequest().getPageSize())
        .orderBy(searchOrderBy(request.sort()))
        .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(inquiry.count())
      .from(inquiry)
      .where(
        searchCategory(request.category()),
        searchKeyword(request.searchKeyword())
      );

    return PageableExecutionUtils.getPage(inquiryList, request.pageRequest(), countQuery::fetchOne);

  }

  private JPAQuery<ListInquiryDto> selectFields() {
    return queryFactory
      .select(
        Projections.fields(
          ListInquiryDto.class,
          inquiry.id.as("inquiryId"),
          inquiry.category.stringValue().as("categoryCode"),
          inquiry.category.stringValue().as("categoryName"),
          inquiry.title,
          inquiry.member.name.as("createdName"),
          inquiry.createdTime,
          inquiry.viewCount,
          inquiry.publicYn,
          inquiry.solvedYn
        )
      );
  }

  /**
   * 카테고리 검색
   */
  private static Predicate searchCategory(InquiryCategory category) {
    if (category == null) {
      return null;
    }
    return inquiry.category.eq(category);
  }

  /**
   * 키워드 검색(제목, 작성자)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(inquiry.title.contains(searchKeyword));
    builder.or(inquiry.member.name.contains(searchKeyword));
    return builder;
  }

  /**
   * 정렬 조건
   */
  private OrderSpecifier<?> searchOrderBy(InquirySort sort) {
    if (sort == null) {
      return inquiry.createdTime.desc();
    }

    return switch (sort) {
      case LATEST -> inquiry.createdTime.desc();
      case VIEW_COUNT -> inquiry.viewCount.desc();
    };
  }

  /**
   * 마이페이지 게시글 정보
   */
  public List<ListInquiryDto> getMyPageInquiry(String handle) {
    return selectFields()
      .from(inquiry)
      .where(inquiry.member.handle.eq(handle))
      .orderBy(inquiry.createdTime.desc())
      .fetch();
  }

}
