package org.example.domain.week.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.generation.repository.GenerationRepository;
import org.example.domain.week.controller.response.DetailWeekResponse;
import org.example.domain.week.repository.DetailWeekRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailWeekService {

  private final DetailWeekRepository detailWeekRepository;
  private final GenerationRepository generationRepository;

  /**
   * 현재 주차 정보 조회
   */
  public DetailWeekResponse getWeek() {
    DetailWeekResponse week = detailWeekRepository.getWeek();
    if (week == null) {
      Integer generation = generationRepository.findTopByOrderByValueDesc().getValue();
      throw new GeneralException(ErrorStatus.NOT_FOUND, generation + "기 스터디 진행 기간이 아닙니다.");
    }
    return week;
  }
}
