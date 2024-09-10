package org.example.domain.problem.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.algorithm.repository.AlgorithmRepository;
import org.example.domain.algorithm.service.CreateAlgorithmService;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem_algorithm.ProblemAlgorithm;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.example.email.enums.EmailType;
import org.example.email.service.CoreEmailService;
import org.example.schedule.solved_ac.SolvedAcClient;
import org.example.schedule.solved_ac.response.problem.AlgorithmDto;
import org.example.schedule.solved_ac.response.problem.ProblemDto;
import org.example.schedule.solved_ac.response.problem.ProblemResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CreateProblemService {

  private final CoreEmailService coreEmailService;
  private final CreateAlgorithmService createAlgorithmService;
  private final SolvedAcClient solvedAcClient;
  private final ProblemRepository problemRepository;
  private final AlgorithmRepository algorithmRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;
  private static final int NUMBER_PER_PAGE = 50;
  private static final String QUERY = "";
  private static final String SORT = "id";
  private static final String DIRECTION = "asc";

  @Value("${spring.mail.username}")
  private String koalaEmail;

  @Value("${spring.profiles.active}")
  private String profiles;

  @Async
  public void createProblem() {
    try {
      createAlgorithmService.createAlgorithm();

      problemAlgorithmRepository.deleteAll();
      ProblemResponse initResponse = solvedAcClient.searchProblems(1, QUERY, SORT, DIRECTION);
      int pageCount = initResponse.getCount() / NUMBER_PER_PAGE + 1;

      for (int page = 1; page <= pageCount; page++) {
        log.info("{}번 페이지 문제 저장", page);
        ProblemResponse problemResponse = solvedAcClient.searchProblems(page, QUERY, SORT, DIRECTION);

        // Level 미할당 문제 제거
        List<ProblemDto> problemDtoList = problemResponse.getProblemList()
          .stream()
          .filter(problem -> problem.getLevel() != 0)
          .toList();

        for (ProblemDto problemDto : problemDtoList) {
          Problem problem = problemRepository.save(problemDto.toEntity());

          // 문제 알고리즘 매핑
          for (AlgorithmDto algorithmDto : problemDto.getAlgorithmList()) {
            algorithmRepository.findById(algorithmDto.getName())
              .ifPresent(algorithm ->
                problemAlgorithmRepository.save(
                  ProblemAlgorithm.builder()
                    .problem(problem)
                    .algorithm(algorithm)
                    .build()
                )
              );
          }
        }
      }

      coreEmailService.send(koalaEmail, EmailType.BAEKJOON_SCHEDULER.toString(), "[" + profiles + "] 백준 문제 갱신 성공");
    } catch (Exception e) {
      coreEmailService.send(koalaEmail, EmailType.BAEKJOON_SCHEDULER.toString(), e.getMessage());
      throw new GeneralException(ErrorStatus.BAD_REQUEST, e.getMessage());
    }


  }
}
