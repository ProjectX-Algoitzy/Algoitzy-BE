package org.example.domain.challenge_reward.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge_reward.controller.response.MyChallengeRewardStatusResponse;
import org.example.domain.challenge_reward.service.ChallengeRewardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge/reward")
@RequiredArgsConstructor
@Tag(name = "ChallengeRewardController", description = "[USER] 챌린지 보상 관련 API")
public class ChallengeRewardController {

  private final ChallengeRewardService challengeRewardService;

  @GetMapping("/status")
  @Operation(summary = "내 보상 현황 확인")
  public ApiResponse<MyChallengeRewardStatusResponse> getMyChallengeRewardStatus() {
    return ApiResponse.onSuccess(challengeRewardService.getMyChallengeRewardStatus());
  }
}
