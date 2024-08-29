package org.example.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreProblemService {

  private final ProblemRepository problemRepository;

  public Problem findByNumber(Integer number) {
    return problemRepository.findById(number)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 문제 ID입니다."));
  }
}
