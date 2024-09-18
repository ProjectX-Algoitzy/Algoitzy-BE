package org.example.domain.study_member.repository;

import static org.example.domain.study_member.QStudyMember.studyMember;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.attendance.controller.response.ListAttendanceDto;
import org.example.domain.study.Study;
import org.example.domain.study_member.controller.response.ListTempStudyMemberDto;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ListStudyMemberRepository {

  private final JPAQueryFactory queryFactory;

  public List<ListAttendanceDto> getStudyMemberList(Study study) {
    return queryFactory
      .select(Projections.fields(
          ListAttendanceDto.class,
          studyMember.member.name
        )
      )
      .from(studyMember)
      .where(
        studyMember.study.eq(study),
        studyMember.status.eq(StudyMemberStatus.PASS)
      )
      .fetch();
  }

  /**
   * 자율 스터디원 목록 조회
   */
  public List<ListTempStudyMemberDto> getTempStudyMemberList(Long studyId) {
    return queryFactory
      .select(
        Projections.fields(
          ListTempStudyMemberDto.class,
          studyMember.id.as("studyMemberId"),
          studyMember.member.name,
          studyMember.member.phoneNumber,
          studyMember.status
        )
      )
      .from(studyMember)
      .where(
        studyMember.study.id.eq(studyId)
      )
      .fetch();
  }
}
