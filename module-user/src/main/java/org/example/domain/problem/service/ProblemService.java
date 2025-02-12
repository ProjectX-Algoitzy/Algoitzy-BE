package org.example.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

  private final CreateProblemService createProblemService;

  public void createProblem() {
    createProblemService.createProblem();
  }
}
