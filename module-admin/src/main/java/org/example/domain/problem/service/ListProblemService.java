package org.example.domain.problem.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.problem.Level;
import org.example.domain.problem.controller.request.SearchProblemRequest;
import org.example.domain.problem.controller.response.ListProblemDto;
import org.example.domain.problem.controller.response.ListProblemResponse;
import org.example.domain.problem.repository.ListProblemRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListProblemService {

  private final ListProblemRepository listProblemRepository;

  /**
   * 백준 문제 목록 조회
   */
  public ListProblemResponse getProblemList(SearchProblemRequest request) {
    Page<ListProblemDto> page = listProblemRepository.getProblemList(request);
    for (ListProblemDto problem : page.getContent()) {
      problem.setLevelUrl(Level.valueOf(problem.getLevelUrl()));
    }

    return ListProblemResponse.builder()
      .problemList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
