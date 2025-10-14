package org.example.domain.challenge_problem.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.challenge_problem.controller.response.DetailChallengeProblemResponse;
import org.example.domain.challenge_problem.repository.ChallengeProblemRepository;
import org.example.domain.problem.Problem;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailChallengeProblemService {

  private final ChallengeProblemRepository challengeProblemRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;

  public DetailChallengeProblemResponse getChallengeProblem() {
    ChallengeProblem challengeProblem =
      challengeProblemRepository.findByDate(LocalDate.now()).orElseThrow(() ->
        new GeneralException(ErrorStatus.NOTICE_NOT_FOUND, "금일 데일리 챌린지는 00시 10분에 공개됩니다."));

    Problem problem = challengeProblem.getProblem();
    List<String> algorithmList = problemAlgorithmRepository.findAllByProblem(problem).stream()
      .map(problemAlgorithm -> problemAlgorithm.getAlgorithm().getKoreanName())
      .toList();

    return DetailChallengeProblemResponse.builder()
      .problemNumber(problem.getNumber())
      .level(problem.getLevel())
      .levelImageUrl(problem.getLevel().getImageUrl())
      .algorithmList(algorithmList)
      .build();
  }
}
