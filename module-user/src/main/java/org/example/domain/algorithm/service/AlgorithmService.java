package org.example.domain.algorithm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlgorithmService {

  private final CreateAlgorithmService createAlgorithmService;

  /**
   * 백준 알고리즘 저장
   */
  public void createAlgorithm() {
    createAlgorithmService.createAlgorithm();
  }
}
