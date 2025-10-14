package org.example.domain.challenge_reward_log.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_reward_log.controller.request.SearchChallengeRewardLogRequest;
import org.example.domain.challenge_reward_log.controller.response.ListChallengeRewardLogResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeRewardLogService {

  private final ListChallengeRewardLogService listChallengeRewardLogService;

  /**
   * 내 챌린지 보상 이력 조회
   */
  public ListChallengeRewardLogResponse getChallengeRewardLogList(SearchChallengeRewardLogRequest request) {
    return listChallengeRewardLogService.getChallengeRewardLogList(request);
  }
}
