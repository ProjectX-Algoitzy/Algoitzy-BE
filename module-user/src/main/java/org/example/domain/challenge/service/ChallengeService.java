package org.example.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

  private final DetailChallengeService detailChallengeService;

  /**
   * 로그인 유저 금일 챌린지 참여 여부 확인
   */
  public Boolean checkChallengeJoin() {
    return detailChallengeService.checkChallengeJoin();
  }
}
