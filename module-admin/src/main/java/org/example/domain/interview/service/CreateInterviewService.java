package org.example.domain.interview.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.interview.Interview;
import org.example.domain.interview.controller.request.UpdateInterviewRequest;
import org.example.domain.interview.repository.ListInterviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateInterviewService {

  private final CoreInterviewService coreInterviewService;
  private final ListInterviewRepository listInterviewRepository;

  /**
   * 면접 일정 수정
   */
  public void updateInterview(UpdateInterviewRequest request) {
    Interview interview = coreInterviewService.findById(request.interviewId());

    try {
      SimpleDateFormat inputFormat = new SimpleDateFormat("MMddHHmm");
      Date date = inputFormat.parse(request.time());
      SimpleDateFormat outputFormat = new SimpleDateFormat("M월 d일 HH:mm");

      List<String> interviewTimeList = listInterviewRepository.getInterviewTimeList();
      String time = outputFormat.format(date);
      if (interviewTimeList.contains(time)) {
        throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "면접 일정은 중복될 수 없습니다.");
      }
      interview.updateTime(time);

    } catch (ParseException e) {
      throw new GeneralException(ErrorStatus.INTERNAL_ERROR, "면접 일정 변환 중 오류가 발생했습니다.");
    }
  }
}
