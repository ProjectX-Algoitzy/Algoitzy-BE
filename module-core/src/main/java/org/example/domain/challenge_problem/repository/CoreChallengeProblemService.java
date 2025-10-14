package org.example.domain.challenge_problem.repository;

import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreChallengeProblemService {

  private final ChallengeProblemRepository challengeProblemRepository;

  public ChallengeProblem findById(LocalDate localDate) {
    return challengeProblemRepository.findById(localDate)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 챌린지 문제 ID입니다."));
  }
}
