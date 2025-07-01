package org.example.domain.challenge_winner.repository;

import java.util.List;
import org.example.domain.challenge_winner.ChallengeWinner;
import org.example.domain.member.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeWinnerRepository extends JpaRepository<ChallengeWinner, Long> {

  @EntityGraph(attributePaths = {"challengeProblem", "challengeProblem.problem"})
  List<ChallengeWinner> findChallengeWinnerByMember(Member member);

  void deleteChallengeWinnerByMember(Member member);
}
