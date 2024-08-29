package org.example.domain.interview.repository;

import static org.example.domain.generation.QGeneration.generation;
import static org.example.domain.interview.QInterview.interview;
import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListInterviewRepository {

  private final JPAQueryFactory queryFactory;

  /**
   * 현재 기수 면접 일정 목록 조회
   */
  public List<String> getInterviewTimeList() {
    Integer maxGeneration = queryFactory
      .select(generation.value.max())
      .from(generation)
      .fetchOne();

    return queryFactory
      .select(interview.time)
      .from(interview)
      .innerJoin(studyMember).on(interview.studyMember.eq(studyMember))
      .innerJoin(study).on(studyMember.study.eq(study))
      .innerJoin(generation).on(study.generation.eq(generation))
      .where(generation.value.eq(maxGeneration))
      .fetch();
  }
}
