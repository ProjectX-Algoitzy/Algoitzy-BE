package org.example.domain.challenge_reward.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_join_log.repository.DetailChallengeJoinLogRepository;
import org.example.domain.challenge_problem.repository.CoreChallengeProblemService;
import org.example.domain.challenge_reward.ChallengeReward;
import org.example.domain.challenge_reward.repository.ChallengeRewardRepository;
import org.example.domain.challenge_reward_log.ChallengeRewardLog;
import org.example.domain.challenge_reward_log.repository.ChallengeRewardLogRepository;
import org.example.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateChallengeRewardService {

  private final DetailChallengeJoinLogRepository detailChallengeJoinLogRepository;
  private final ChallengeRewardLogRepository challengeRewardLogRepository;
  private final ChallengeRewardRepository challengeRewardRepository;
  private final CoreChallengeProblemService coreChallengeProblemService;

  /**
   * 챌린지 보상 생성
   */
  public void createChallengeReward() {
    List<ChallengeJoinLog> challengeJoinLogList = new ArrayList<>();
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.CPP));
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.JAVA));
    challengeJoinLogList.add(detailChallengeJoinLogRepository.getRewardTarget(LanguageType.PYTHON));

    // 보상 중복 획득 불가능
    Set<Member> rewardTargetMemberSet = new HashSet<>();
    for (ChallengeJoinLog challengeJoinLog : challengeJoinLogList) {
      if (challengeJoinLog == null) continue;
      rewardTargetMemberSet.add(challengeJoinLog.getMember());
    }

    for (Member member : rewardTargetMemberSet) {
      ChallengeReward challengeReward = challengeRewardRepository.save(
        ChallengeReward.builder()
//          .challengeProblem(coreChallengeProblemService.findById(LocalDate.now().minusDays(1)))
          .member(member)
          .build()
      );

      challengeRewardLogRepository.save(
        ChallengeRewardLog.builder()
          .challengeReward(challengeReward)
          .rewardCount(challengeRewardRepository.countChallengeRewardByMember(member))
          .build()
      );
    }

  }
}
