package org.example.domain.challenge_reward.repository;

import org.example.domain.challenge_reward.ChallengeReward;
import org.example.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRewardRepository extends JpaRepository<ChallengeReward, Long> {

  long countChallengeRewardByMember(Member member);
}
