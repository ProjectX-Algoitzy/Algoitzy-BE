package org.example.domain.study_member.repository;

import org.example.domain.study_member.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyMemberRepository extends JpaRepository<StudyMember, Long> {

}
