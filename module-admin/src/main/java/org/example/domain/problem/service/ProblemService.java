package org.example.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

  private final CreateProblemService createProblemService;

  /**
   * 백준 문제 저장
   */
  public void createProblem() {
    createProblemService.createProblem();
  }

}
