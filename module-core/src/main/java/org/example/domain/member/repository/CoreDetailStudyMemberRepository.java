package org.example.domain.member.repository;

import static org.example.domain.study.QStudy.study;
import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.domain.study.enums.StudyType;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreDetailStudyMemberRepository {

  private final JPAQueryFactory queryFactory;


  public boolean isRegularStudyMember() {
    return !queryFactory
      .selectFrom(studyMember)
      .innerJoin(study).on(studyMember.study.eq(study))
      .where(
        study.type.eq(StudyType.REGULAR),
        studyMember.member.email.eq(SecurityUtils.getCurrentMemberEmail()),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .fetch()
      .isEmpty();
  }

}
