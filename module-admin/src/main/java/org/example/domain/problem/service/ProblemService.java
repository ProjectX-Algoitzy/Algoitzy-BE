package org.example.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.problem.controller.request.SearchProblemRequest;
import org.example.domain.problem.controller.response.ListProblemResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

  private final CreateProblemService createProblemService;
  private final ListProblemService listProblemService;

  /**
   * 백준 문제 저장
   */
  public void createProblem() {
    createProblemService.createProblem();
  }

  /**
   * 백준 문제 목록 조회
   */
  public ListProblemResponse getProblemList(SearchProblemRequest request) {
    return listProblemService.getProblemList(request);
  }
}
