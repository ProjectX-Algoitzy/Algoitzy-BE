package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final CoreListAnswerService coreListAnswerService;
  private final DetailAnswerService detailAnswerService;

  /**
   * 작성한 지원서 목록 조회
   */
  public ListAnswerResponse getAnswerList(SearchAnswerRequest request) {
    return coreListAnswerService.getAnswerList(request);
  }

  /**
   * 작성한 지원서 상세 조회
   */
  public DetailAnswerResponse getAnswer(Long answerId) {
    return detailAnswerService.getAnswer(answerId);
  }
}
