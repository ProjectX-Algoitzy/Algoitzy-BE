package org.example.domain.algorithm.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.algorithm.Algorithm;
import org.example.domain.algorithm.repository.AlgorithmRepository;
import org.example.schedule.solved_ac.SolvedAcClient;
import org.example.schedule.solved_ac.response.algorithm.AlgorithmDto;
import org.example.schedule.solved_ac.response.algorithm.AlgorithmResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateAlgorithmService {

  private final SolvedAcClient solvedAcClient;
  private final AlgorithmRepository algorithmRepository;
  private static final int NUMBER_PER_PAGE = 30;

  public void createAlgorithm() {
    AlgorithmResponse initResponse = solvedAcClient.searchTag(1);
    int pageCount = initResponse.getCount() / NUMBER_PER_PAGE + 1;

    for (int page = 1; page <= pageCount; page++) {
      log.info("{}번 페이지 알고리즘 저장", page);
      AlgorithmResponse algorithmResponse = solvedAcClient.searchTag(1);
      List<AlgorithmDto> algorithmDtoList = algorithmResponse.getAlgorithmList();

      List<Algorithm> algorithmList = new ArrayList<>();
      for (AlgorithmDto algorithmDto : algorithmDtoList) {
        algorithmList.add(algorithmDto.toEntity());
      }
      algorithmRepository.saveAll(algorithmList);
    }
  }
}
