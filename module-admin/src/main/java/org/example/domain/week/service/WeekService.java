package org.example.domain.week.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.week.controller.response.DetailWeekResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeekService {

  private final DetailWeekService detailWeekService;

  /**
   * 현재 주차 정보 조회
   */
  public DetailWeekResponse getWeek() {
    return detailWeekService.getWeek();
  }
}
