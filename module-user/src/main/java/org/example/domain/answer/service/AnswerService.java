package org.example.domain.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.answer.controller.request.CreateAnswerRequest;
import org.example.domain.answer.controller.request.SearchAnswerRequest;
import org.example.domain.answer.controller.response.DetailAnswerResponse;
import org.example.domain.answer.controller.response.ListAnswerResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

  private final CreateAnswerService createAnswerService;
  private final CoreListAnswerService coreListAnswerService;
  private final CoreDetailAnswerService coreDetailAnswerService;

  /**
   * 지원서 작성
   */
  public void createAnswer(Long applicationId, CreateAnswerRequest request) {
    createAnswerService.createAnswer(applicationId, request);
  }

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
    return coreDetailAnswerService.getAnswer(answerId);
  }

  public void deleteAnswer(Long answerId) {
    createAnswerService.deleteAnswer(answerId);
  }
}
