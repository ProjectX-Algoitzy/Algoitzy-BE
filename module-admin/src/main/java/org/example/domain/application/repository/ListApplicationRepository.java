package org.example.domain.application.repository;

import static org.example.domain.application.QApplication.application;
import static org.example.domain.study.QStudy.study;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.application.controller.request.SearchApplicationRequest;
import org.example.domain.application.controller.response.ListApplicationDto;
import org.example.domain.member.QMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListApplicationRepository {

  private final JPAQueryFactory queryFactory;
  private final QMember create = new QMember("create");
  private final QMember update = new QMember("update");


  public List<ListApplicationDto> getApplicationList(SearchApplicationRequest request) {
    return queryFactory
      .select(Projections.fields(ListApplicationDto.class,
        application.id.as("applicationId"),
        application.title,
        study.name.as("studyName"),
        create.name.as("createdName"),
        application.createdTime,
        update.name.as("updatedName"),
        application.updatedTime
        )
      )
      .from(application)
      .innerJoin(study).on(application.study.eq(study))
      .innerJoin(create).on(application.createdBy.eq(create.email))
      .innerJoin(update).on(application.updatedBy.eq(update.email))
      .where(
        study.generation.eq(request.generation())
      )
      .fetch();
  }
}
