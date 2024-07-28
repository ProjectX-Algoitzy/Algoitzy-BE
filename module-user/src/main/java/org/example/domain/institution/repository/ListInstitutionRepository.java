package org.example.domain.institution.repository;

import static org.example.domain.institution.QInstitution.institution;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.request.SearchInstitutionRequest;
import org.example.domain.institution.controller.response.ListInstitutionDto;
import org.example.domain.institution.enums.InstitutionSort;
import org.springframework.data.domain.Page;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class ListInstitutionRepository {

  private final JPAQueryFactory queryFactory;

  public Page<ListInstitutionDto> getInstitutionList(SearchInstitutionRequest request) {
    List<ListInstitutionDto> institutionList = queryFactory
      .select(Projections.fields(
          ListInstitutionDto.class,
          institution.id.as("institutionId"),
          institution.name
          // todo 조회수 로직 추가 후 수정
        )
      )
      .from(institution)
      .where(
        institution.type.eq(request.type()),
        searchKeyword(request.searchKeyword())
      )
      .offset(request.pageRequest().getOffset())
      .limit(request.pageRequest().getPageSize())
      .orderBy(
        searchOrderBy(request.sort())
      )
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(institution.count())
      .from(institution)
      .where(
        institution.type.eq(request.type()),
        searchKeyword(request.searchKeyword())
      );

    return PageableExecutionUtils.getPage(institutionList, request.pageRequest(), countQuery::fetchOne);

  }

  /**
   * 키워드 검색(기관명)
   */
  private Predicate searchKeyword(String searchKeyword) {
    if (!StringUtils.hasText(searchKeyword)) {
      return null;
    }
    BooleanBuilder builder = new BooleanBuilder();
    builder.or(institution.name.contains(searchKeyword));
    return builder;
  }

  /**
   * 정렬 조건
   */
  private OrderSpecifier<?> searchOrderBy(InstitutionSort sort) {
    if (sort == null) {
      return institution.id.asc();
    }

    return switch (sort) {
      case NAME -> institution.name.asc();
      // todo 조회수 로직 추가 후 수정
      case VIEW_COUNT -> institution.content.asc();
    };
  }
}
