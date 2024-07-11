package org.example.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.service.CreateProblemService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {

  private final CreateProblemService createProblemService;

  /**
   * 매주 수요일 문제 크롤링
   */
  @Scheduled(cron = "0 0 4 * * WED")
  public void crawlingProblem() {
    log.info("=========백준 문제 저장 스케쥴러 실행=========");
    createProblemService.createProblem();
  }
}
