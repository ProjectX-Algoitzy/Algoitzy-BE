package org.example.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.service.CreateProblemService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {

  private final CreateProblemService createProblemService;

  /**
   * 매주 수요일 문제 크롤링
   */
  @Async
//  @Scheduled(cron = "0 0 4 * * WED")
  @Scheduled(cron = "*/5 * * * * *")
  public void crawlingProblem() {
    log.info("=========백준 문제 크롤링 실행=========");
    createProblemService.searchProblems();
  }
}
