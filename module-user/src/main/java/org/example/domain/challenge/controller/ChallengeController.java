package org.example.domain.challenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge.service.ChallengeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Tag(name = "ChallengeController", description = "[USER] 챌린지 관련 API")
public class ChallengeController {

  private final ChallengeService challengeService;

  @GetMapping("/check-join")
  @Operation(summary = "로그인 유저 금일 챌린지 참여 여부 확인")
  public ApiResponse<Boolean> checkChallengeJoin() {
    return ApiResponse.onSuccess(challengeService.checkChallengeJoin());
  }
}
