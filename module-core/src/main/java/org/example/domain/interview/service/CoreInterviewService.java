package org.example.domain.interview.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.interview.Interview;
import org.example.domain.interview.repository.InterviewRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoreInterviewService {

  private final InterviewRepository interviewRepository;

  public Interview findById(Long interviewId) {
    return interviewRepository.findById(interviewId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 interview id입니다."));
  }
}
