package org.example.domain.challenge_reward.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "내 보상 현황 응답 객체")
public class MyChallengeRewardStatusResponse {

  @Schema(description = "보상 개수")
  private long rewardCount;

  @Schema(description = "1등 횟수(보상 전환 X)")
  private long winCount;

}
