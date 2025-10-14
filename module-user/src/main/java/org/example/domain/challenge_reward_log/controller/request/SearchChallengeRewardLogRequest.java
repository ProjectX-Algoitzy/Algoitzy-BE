package org.example.domain.challenge_reward_log.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import org.example.domain.challenge_reward_log.enums.ChallengeRewardLogType;

@Schema(description = "챌린지 보상 이력 목록 요청 객체")
public record SearchChallengeRewardLogRequest(

  @Schema(description = "보상 이력 유형", allowableValues = {"USED", "ACQUIRED"})
  ChallengeRewardLogType logType
) {

}
