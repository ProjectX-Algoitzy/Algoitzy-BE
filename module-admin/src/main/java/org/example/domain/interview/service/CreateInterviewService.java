package org.example.domain.interview.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.interview.Interview;
import org.example.domain.interview.controller.request.UpdateInterviewRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateInterviewService {

  private final CoreInterviewService coreInterviewService;

  /**
   * 면접 일정 수정
   */
  public void updateInterview(UpdateInterviewRequest request) {
    Interview interview = coreInterviewService.findById(request.interviewId());
    interview.updateTime(request.time());
  }
}
