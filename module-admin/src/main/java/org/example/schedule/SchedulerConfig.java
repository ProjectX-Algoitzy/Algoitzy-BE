package org.example.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.attendance.service.CreateAttendanceService;
import org.example.domain.problem.service.CreateProblemService;
import org.example.domain.workbook.service.CreateWorkbookService;
import org.example.schedule.service.CreateViewCountService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SchedulerConfig {

  private final CreateProblemService createProblemService;
  private final CreateWorkbookService createWorkbookService;
  private final CreateAttendanceService createAttendanceService;
  private final CreateViewCountService createViewCountService;

  /**
   * 매주 수요일 00:00 백준 문제 저장
   */
  @Scheduled(cron = "0 0 4 * * WED")
  public void createProblem() {
    log.info("=========백준 문제 저장 스케쥴러 실행=========");
    createProblemService.createProblem();
  }

  /**
   * 매주 금요일 00:00 정규 스터디 문제집 생성
   */
  @Scheduled(cron = "0 0 0 * * FRI")
  public void createAutoWorkbook() {
    log.info("=========문제집 생성 스케쥴러 실행=========");
    createWorkbookService.createAutoWorkbook();
  }

  /**
   * 매주 월요일 07:00 출석부 갱신
   */
  @Scheduled(cron = "0 0 7 * * MON")
  public void createAttendance() {
    log.info("=========출석부 갱신 스케쥴러 실행=========");
    createAttendanceService.createAttendance();
  }

  /**
   * 10분마다 조회수 동기화
   */
  @Scheduled(fixedDelay = 600000L)
  public void syncViewCount() {
    log.info("=========조회수 동기화 스케쥴러 실행=========");
    createViewCountService.syncViewCount();
  }
}
