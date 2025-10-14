package org.example.domain.challenge_reward_log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChallengeRewardLogType {
  ACQUIRED("획득"),
  USED("사용");

  private final String type;
}
