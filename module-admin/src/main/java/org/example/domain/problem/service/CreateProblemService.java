package org.example.domain.problem.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem.response.ProblemResponse;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.example.schedule.SolvedAcProblemSearchClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProblemService {

  private final SolvedAcProblemSearchClient solvedAcProblemSearchClient;

  private final ProblemRepository problemRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;

  public void searchProblems() {

    int page = 2;
    String query = "";
    String sort = "random";
    String direction = "asc";
    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);


    ProblemResponse<List<Problem>> problemList = solvedAcProblemSearchClient.searchProblems(page, query, sort, direction);
    System.out.println("problemList = " + problemList);

  }

}
