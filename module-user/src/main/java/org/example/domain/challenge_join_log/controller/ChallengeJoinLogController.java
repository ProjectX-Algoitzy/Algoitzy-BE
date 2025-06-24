package org.example.domain.challenge_join_log.controller;

import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.challenge_join_log.service.CreateChallengeJoinLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo delete
@RestController
@RequestMapping("/challenge-join-log")
@RequiredArgsConstructor
public class ChallengeJoinLogController {

  private final CreateChallengeJoinLogService createChallengeJoinLogService;

  @GetMapping
  public ApiResponse<Void> getChallengeJoinLog() {
    createChallengeJoinLogService.createChallengeJoinLog();
    return ApiResponse.onSuccess();
  }
}
