package org.example.domain.challenge_problem.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_problem.controller.response.DetailChallengeProblemResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeProblemService {

  private final DetailChallengeProblemService detailChallengeProblemService;

  /**
   * 금일 챌린지 문제 상세 조회
   */
  public DetailChallengeProblemResponse getChallengeProblem() {
    return detailChallengeProblemService.getChallengeProblem();
  }
}
