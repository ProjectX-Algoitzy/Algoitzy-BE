package org.example.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.service.CreateProblemService;
import org.example.domain.workbook.service.CreateWorkbookService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {

  private final CreateProblemService createProblemService;
  private final CreateWorkbookService createWorkbookService;

  /**
   * 매주 수요일 00:00 백준 문제 저장
   */
  @Scheduled(cron = "0 0 4 * * WED")
  public void createProblem() {
    log.info("=========백준 문제 저장 스케쥴러 실행=========");
    createProblemService.createProblem();
  }

  /**
   * 매주 금요일 00:00 코딩테스트 대비반 문제집 생성
   */
  @Scheduled(cron = "0 0 0 * * FRI")
  public void createWorkbook() {
    log.info("=========코딩테스트 대비반 문제집 생성 스케쥴러 실행=========");
    createWorkbookService.createCodingTestPrepareWorkbook();
  }
}
