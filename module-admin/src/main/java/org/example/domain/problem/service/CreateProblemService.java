package org.example.domain.problem.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.algorithm.Algorithm;
import org.example.domain.algorithm.repository.AlgorithmRepository;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem_algorithm.ProblemAlgorithm;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.example.schedule.solved_ac.SolvedAcClient;
import org.example.schedule.solved_ac.response.problem.AlgorithmDto;
import org.example.schedule.solved_ac.response.problem.ProblemDto;
import org.example.schedule.solved_ac.response.problem.ProblemResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateProblemService {

  private final SolvedAcClient solvedAcClient;
  private final ProblemRepository problemRepository;
  private final AlgorithmRepository algorithmRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;
  private static final int NUMBER_PER_PAGE = 50;
  private static final String QUERY = "";
  private static final String SORT = "id";
  private static final String DIRECTION = "asc";

  @Async
  public void createProblem() {
    try {
      ProblemResponse initResponse = solvedAcClient.searchProblems(1, QUERY, SORT, DIRECTION);
      int pageCount = initResponse.getCount() / NUMBER_PER_PAGE + 1;

      for (int page = 1; page <= pageCount; page++) {
        log.info("{}번 페이지 문제 저장", page);
        ProblemResponse problemResponse = solvedAcClient.searchProblems(page, QUERY, SORT, DIRECTION);

        List<ProblemDto> problemDtoList = problemResponse.getProblemList()
          .stream()
          .filter(problem -> problem.getLevel() != 0)
          .toList();

        for (ProblemDto problemDto : problemDtoList) {
          Problem problem = problemRepository.save(problemDto.toEntity());
          for (AlgorithmDto algorithmDto : problemDto.getAlgorithmList()) {
            Optional<Algorithm> optionalAlgorithm = algorithmRepository.findById(algorithmDto.getName());
            if (optionalAlgorithm.isPresent()) {
              Algorithm algorithm = optionalAlgorithm.get();
              problemAlgorithmRepository.save(
                ProblemAlgorithm.builder()
                  .problem(problem)
                  .algorithm(algorithm)
                  .build()
              );
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("SolveAc 문제 저장 중 오류 발생 : {}", e.getMessage());
    }
  }
}
