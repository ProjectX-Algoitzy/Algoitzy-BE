package org.example.domain.study_member.repository;

import java.util.List;
import java.util.Optional;
import org.example.domain.member.Member;
import org.example.domain.study.Study;
import org.example.domain.study_member.StudyMember;
import org.example.domain.study_member.enums.StudyMemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

  Optional<StudyMember> findByStudyAndMember(Study study, Member member);
  Optional<StudyMember> findByStudyAndMemberAndStatus(Study study, Member member, StudyMemberStatus status);

  List<StudyMember> findAllByMember(Member member);

  Optional<StudyMember> findByMemberAndStatus(Member member, StudyMemberStatus status);
  List<StudyMember> findAllByStudyIdAndStatus(Long studyId, StudyMemberStatus status);
}
