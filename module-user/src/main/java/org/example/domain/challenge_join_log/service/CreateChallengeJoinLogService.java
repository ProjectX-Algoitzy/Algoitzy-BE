package org.example.domain.challenge_join_log.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.ChallengeJoinLog;
import org.example.domain.challenge_join_log.enums.LanguageType;
import org.example.domain.challenge_join_log.repository.ChallengeJoinLogRepository;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.challenge_problem.repository.CoreChallengeProblemService;
import org.example.domain.member.Member;
import org.example.domain.member.repository.MemberRepository;
import org.example.util.http_request.Url;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateChallengeJoinLogService {

  private final CoreChallengeProblemService coreChallengeProblemService;
  private final ChallengeJoinLogRepository challengeJoinLogRepository;
  private final MemberRepository memberRepository;

  private final WebDriver webDriver;

  public void createChallengeJoinLog() {
    // todo 00:00에는 전날 정산

    ChallengeProblem challengeProblem = coreChallengeProblemService.findById(LocalDate.now());
    challengeJoinLogRepository.deleteByChallengeProblem(challengeProblem);

    List<Member> memberList = memberRepository.findAll();
    for (Member member : memberList) {
      ChallengeJoinLog cppLog = crawlLog(member, challengeProblem, LanguageType.CPP);
      if (cppLog != null) challengeJoinLogRepository.save(cppLog);
      ChallengeJoinLog pythonLog = crawlLog(member, challengeProblem, LanguageType.PYTHON);
      if (pythonLog != null) challengeJoinLogRepository.save(pythonLog);
      ChallengeJoinLog javaLog = crawlLog(member, challengeProblem, LanguageType.JAVA);
      if (javaLog != null) challengeJoinLogRepository.save(javaLog);

    }

  }

  /**
   * 백준 채점 현황 크롤링
   */
  private ChallengeJoinLog crawlLog(Member member, ChallengeProblem challengeProblem, LanguageType languageType) {
    webDriver.get(Url.BAEKJOON_STATUS.getBaekjoonStatusUrl(challengeProblem.getProblem().getNumber(), member.getHandle(), languageType));
    List<WebElement> memoryList = webDriver.findElements(By.className("memory"));
    if (memoryList.isEmpty()) return null;
    List<WebElement> executionTimeList = webDriver.findElements(By.className("time"));
    List<WebElement> codeLengthList = webDriver.findElements(By.xpath("//td[span[@class='b-text']]"));
    List<WebElement> submitTimeList = webDriver.findElements(By.cssSelector("a.real-time-update.show-date"));

    List<ChallengeJoinLog> challengeJoinLogList = new ArrayList<>();
    for (int i = 0; i < submitTimeList.size(); i++) {
      LocalDateTime submitTime = LocalDateTime.parse(
        submitTimeList.get(i).getAttribute("data-original-title"),
        DateTimeFormatter.ofPattern("yyyy년 M월 d일 HH:mm:ss")
      );
      // 오늘 푼 문제만 인정
      if (!submitTime.toLocalDate().equals(LocalDate.now())) continue;

      challengeJoinLogList.add(
        ChallengeJoinLog.builder()
          .challengeProblem(challengeProblem)
          .member(member)
          .executionTime(Integer.valueOf(executionTimeList.get(i).getText()))
          .codeLength(Integer.valueOf(codeLengthList.get(i).getText()))
          .memory(Integer.valueOf(memoryList.get(i).getText()))
          .submitTime(submitTime)
          .languageType(languageType)
          .build()
      );
    }

    // 실행 시간 > 메모리 > 코드 길이 > 제출 시각
    challengeJoinLogList.sort(
      Comparator.comparingInt(ChallengeJoinLog::getExecutionTime)
        .thenComparingInt(ChallengeJoinLog::getMemory)
        .thenComparingInt(ChallengeJoinLog::getCodeLength)
        .thenComparing(ChallengeJoinLog::getSubmitTime)
    );
    // 언어별 최고 기록 반환
    return challengeJoinLogList.isEmpty() ? null : challengeJoinLogList.get(0);
  }

}
