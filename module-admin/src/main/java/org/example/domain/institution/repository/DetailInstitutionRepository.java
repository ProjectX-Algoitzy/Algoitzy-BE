package org.example.domain.institution.repository;

import static org.example.domain.institution.QInstitution.institution;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.institution.controller.response.DetailInstitutionResponse;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DetailInstitutionRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 기관 상세 조회
   */
  public DetailInstitutionResponse getInstitution(Long institutionId) {
    return queryFactory
      .select(Projections.fields(
          DetailInstitutionResponse.class,
          institution.id.as("institutionId"),
          institution.name,
          institution.content
        )
      )
      .from(institution)
      .where(institution.id.eq(institutionId))
      .fetchOne();
  }
}
