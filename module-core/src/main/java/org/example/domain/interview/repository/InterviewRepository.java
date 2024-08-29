package org.example.domain.interview.repository;

import java.util.Optional;
import org.example.domain.interview.Interview;
import org.example.domain.study_member.StudyMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {

  Optional<Interview> findByStudyMember(StudyMember studyMember);
}
