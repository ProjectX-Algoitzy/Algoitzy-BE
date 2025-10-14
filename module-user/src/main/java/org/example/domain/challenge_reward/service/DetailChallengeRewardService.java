package org.example.domain.challenge_reward.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward.controller.response.MyChallengeRewardStatusResponse;
import org.example.domain.challenge_reward.repository.ChallengeRewardRepository;
import org.example.domain.challenge_winner.repository.ChallengeWinnerRepository;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailChallengeRewardService {


  private final ChallengeWinnerRepository challengeWinnerRepository;
  private final CoreMemberService coreMemberService;
  private final ChallengeRewardRepository challengeRewardRepository;

  /**
   * 내 보상 현황 확인
   */
  public MyChallengeRewardStatusResponse getMyChallengeRewardStatus() {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());

    return MyChallengeRewardStatusResponse.builder()
      .rewardCount(challengeRewardRepository.countChallengeRewardByMember(member))
      .winCount(challengeWinnerRepository.countChallengeWinnerByMember(member))
      .build();
  }
}
