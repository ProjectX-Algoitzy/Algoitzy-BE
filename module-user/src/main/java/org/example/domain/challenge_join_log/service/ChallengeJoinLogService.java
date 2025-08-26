package org.example.domain.challenge_join_log.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.controller.response.ListChallengeJoinLogResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeJoinLogService {

  private final CreateChallengeJoinLogService createChallengeJoinLogService;
  private final ListChallengeJoinLogService listChallengeJoinLogService;

  public void createChallengeJoinLog() {
    createChallengeJoinLogService.createChallengeJoinLog();
  }

  /**
   * 챌린지 이력 목록 조회
   */
  public ListChallengeJoinLogResponse getChallengeJoinLogList() {
    return listChallengeJoinLogService.getChallengeJoinLogList();
  }
}
