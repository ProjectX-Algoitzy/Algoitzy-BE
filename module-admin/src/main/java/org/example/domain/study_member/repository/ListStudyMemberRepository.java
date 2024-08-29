package org.example.domain.study_member.repository;

import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyMemberRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListAttendanceDto> getStudyMemberList(Long studyId) {
    return queryFactory
      .select(Projections.fields(
          ListAttendanceDto.class,
          studyMember.member.name
        )
      )
      .from(studyMember)
      .where(studyMember.study.id.eq(studyId))
      .fetch();
  }
}
