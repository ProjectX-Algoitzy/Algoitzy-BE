package org.example.domain.challenge_reward.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward.controller.request.UseChallengeRewardRequest;
import org.example.domain.challenge_reward.controller.response.MyChallengeRewardStatusResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeRewardService {

  private final DetailChallengeRewardService detailChallengeRewardService;
  private final CreateChallengeRewardService createChallengeRewardService;

  /**
   * 내 보상 현황 확인
   */
  public MyChallengeRewardStatusResponse getMyChallengeRewardStatus() {
    return detailChallengeRewardService.getMyChallengeRewardStatus();
  }

  /**
   * 챌린지 보상 사용
   */
  public void useChallengeReward(UseChallengeRewardRequest request) {
    createChallengeRewardService.useChallengeReward(request);
  }
}
