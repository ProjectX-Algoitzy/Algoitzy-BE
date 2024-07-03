package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.ListAnswerDto;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.example.domain.answer.repository.ListAnswerRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListAnswerService {

  private final ListAnswerRepository listAnswerRepository;

  /**
   * 작성한 지원서 목록 조회
   */
  public ListAnswerResponse getAnswerList(SearchAnswerRequest request) {
    Page<ListAnswerDto> page = listAnswerRepository.getAnswerList(request);
    // 전형 단계 enum to string
    for (ListAnswerDto dto : page.getContent()) {
      dto.updateStatus(dto.getStatus());
    }

    return ListAnswerResponse.builder()
      .answerList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
