package org.example.domain.challenge_reward_log.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge_reward_log.controller.request.SearchChallengeRewardLogRequest;
import org.example.domain.challenge_reward_log.controller.response.ListChallengeRewardLogResponse;
import org.example.domain.challenge_reward_log.service.ChallengeRewardLogService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge/reward/log")
@RequiredArgsConstructor
@Tag(name = "ChallengeRewardLogController", description = "[USER] 챌린지 보상 이력 관련 API")
public class ChallengeRewardLogController {


  private final ChallengeRewardLogService challengeRewardLogService;

  @GetMapping
  @Operation(summary = "내 챌린지 보상 이력 조회")
  public ApiResponse<ListChallengeRewardLogResponse> getChallengeRewardLogList(
    @ParameterObject @ModelAttribute @Valid SearchChallengeRewardLogRequest request
    ) {
    return ApiResponse.onSuccess(challengeRewardLogService.getChallengeRewardLogList(request));
  }
}
