package org.example.domain.problem.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.example.schedule.SolvedAcProblemSearchClient;
import org.example.schedule.solved_ac_response.ProblemResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProblemService {

  private final SolvedAcProblemSearchClient solvedAcProblemSearchClient;

  private final ProblemRepository problemRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;

  public void searchProblems() {

    int page = 1;
    String query = "";
    String sort = "id";
    String direction = "asc";
    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);


    ProblemResponse response = solvedAcProblemSearchClient.searchProblems(page, query, sort, direction);
    System.out.println("problemList = " + response);

  }

}
