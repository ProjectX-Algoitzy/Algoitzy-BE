package org.example.domain.interview.repository;

import static org.example.domain.interview.QInterview.interview;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.field.Field;
import org.example.domain.study.Study;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListInterviewRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 스터디 날짜별 면접자 수
   */
  public long countByStudyAndTime(Study study, Field field) {
    return queryFactory
      .selectFrom(interview)
      .where(
        interview.studyMember.study.eq(study),
        interview.time.eq(field.getContext())
        )
      .fetch().size();
  }
}
