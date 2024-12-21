package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerStatusResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final ListAnswerService listAnswerService;
  private final DetailAnswerService detailAnswerService;

  /**
   * 지원서 전형 단계 목록 조회
   */
  public ListAnswerStatusResponse getAnswerStatusList() {
    return listAnswerService.getAnswerStatusList();
  }
  /**
   * 작성한 지원서 목록 조회
   */
  public ListAnswerResponse getAnswerList(SearchAnswerRequest request) {
    return listAnswerService.getAnswerList(request);
  }

  /**
   * 작성한 지원서 상세 조회
   */
  public DetailAnswerResponse getAnswer(Long answerId) {
    return detailAnswerService.getAnswer(answerId);
  }
}
