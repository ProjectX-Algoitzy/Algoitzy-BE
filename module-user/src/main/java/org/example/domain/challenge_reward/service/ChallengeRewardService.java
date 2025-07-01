package org.example.domain.challenge_reward.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward.controller.response.MyChallengeRewardStatusResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeRewardService {

  private final DetailChallengeRewardService detailChallengeRewardService;

  /**
   * 내 보상 현황 확인
   */
  public MyChallengeRewardStatusResponse getMyChallengeRewardStatus() {
    return detailChallengeRewardService.getMyChallengeRewardStatus();
  }
}
