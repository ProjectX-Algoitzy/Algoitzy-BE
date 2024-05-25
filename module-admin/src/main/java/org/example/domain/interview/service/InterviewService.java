package org.example.domain.interview.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.interview.controller.request.UpdateInterviewRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterviewService {

  private final CreateInterviewService createInterviewService;

  /**
   * 면접 일정 수정
   */
  public void updateInterview(UpdateInterviewRequest request) {
    createInterviewService.updateInterview(request);
  }
}
