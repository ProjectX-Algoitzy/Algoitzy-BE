package org.example.domain.generation.repository;

import static org.example.domain.generation.QGeneration.generation;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.generation.controller.response.ListGenerationDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListGenerationRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 기수 목록 조회
   */
  public List<ListGenerationDto> getGenerationList() {
    return queryFactory
      .select(Projections.fields(
          ListGenerationDto.class,
          generation.id.as("generationId"),
          generation.value.as("generation")
        )
      )
      .from(generation)
      .fetch();
  }
}
