package org.example.domain.challenge_join_log.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge_join_log.controller.response.ListChallengeJoinLogResponse;
import org.example.domain.challenge_join_log.service.ChallengeJoinLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/challenge-join-log")
@RequiredArgsConstructor
public class ChallengeJoinLogController {

  private final ChallengeJoinLogService challengeJoinLogService;

  @Deprecated
  @GetMapping("/crawl")
  public ApiResponse<Void> createChallengeJoinLog() {
    challengeJoinLogService.createChallengeJoinLog();
    return ApiResponse.onSuccess();
  }

  @GetMapping
  @Operation(summary = "챌린지 이력 목록 조회")
  public ApiResponse<ListChallengeJoinLogResponse> getChallengeJoinLogList() {
    return ApiResponse.onSuccess(challengeJoinLogService.getChallengeJoinLogList());
  }
}
