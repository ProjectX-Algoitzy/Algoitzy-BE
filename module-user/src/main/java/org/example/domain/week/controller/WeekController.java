package org.example.domain.week.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.week.controller.response.DetailWeekResponse;
import org.example.domain.week.service.WeekService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/week")
@RequiredArgsConstructor
@Tag(name = "WeekController", description = "[ADMIN] 주차 관련 API")
public class WeekController {

  private final WeekService weekService;

  @GetMapping()
  @Operation(summary = "현재 주차 정보 조회")
  public ApiResponse<DetailWeekResponse> getWeek() {
    return ApiResponse.onSuccess(weekService.getWeek());
  }
}
