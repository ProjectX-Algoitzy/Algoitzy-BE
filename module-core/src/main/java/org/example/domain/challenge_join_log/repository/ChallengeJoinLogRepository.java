package org.example.domain.challenge_join_log.repository;

import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeJoinLogRepository extends JpaRepository<ChallengeJoinLog, Long> {

  void deleteByChallengeProblem(ChallengeProblem challengeProblem);
}
