package org.example.domain.attendance.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import org.example.util.http_request.Url;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class CreateAttendanceServiceTest {

  @Autowired
  WebDriver webDriver;
  @Autowired
  Actions actions;

  @Test
  void 저번주에_푼_문제수() {

    // 페이지 랜딩 대기
    webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl("engus525"));
    new WebDriverWait(webDriver, Duration.ofSeconds(10))
      .until(ExpectedConditions.presenceOfElementLocated(By.tagName("rect")));
    List<WebElement> rectList = webDriver.findElements(By.tagName("rect"));

    // 지난주 범위 계산
    LocalDate today = LocalDate.now().minusDays(7);
    LocalDate lastYear = today.minusYears(1);
    int startRect = today.getDayOfYear()
      - LocalDate.now().get(ChronoField.DAY_OF_WEEK) + 1;
    if (rectList.size() >= 2 * 365) {
      if (lastYear.isLeapYear()) startRect += 365 + 1;
      else startRect += 365;
    }
    int lastRect = startRect + 6;

    int count = 0;
    for (int i = startRect; i <= lastRect; i++) {
      rectList = webDriver.findElements(By.tagName("rect"));
      WebElement rect = rectList.get(i);
      actions.moveToElement(rect).perform();

      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
      // tooltip에 기록된 해당 날짜에 푼 문제 수 추출
      WebElement tooltip = webDriver.findElement(By.className("google-visualization-tooltip"));
      String text = tooltip.getText();
      System.out.println(text);
      int colonIndex = text.indexOf(":");
      if (colonIndex != -1) {
        count += Integer.parseInt(text.substring(colonIndex + 1).strip());
      }
    }

    System.out.println("문제 풀이 수 : " + count);

  }


  @Test
  void 문제집에서_몇개_풀었는지() {

    webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl("engus525"));

    // 맞힌 문제 개수로 제한
    int limitCount = Integer.parseInt(webDriver.findElement(By.id("u-solved")).getText());
    List<Integer> problemNumberList = webDriver.findElements(By.cssSelector(".problem-list a"))
      .stream()
      .map(problemNumber -> Integer.parseInt(problemNumber.getText()))
      .limit(limitCount)
      .toList();

    int solvedCount;
    List<Integer> workbookProblemList = List.of(1446, 20168, 5972, 2151, 16118, 2307);
    System.out.println(workbookProblemList.stream().filter(problemNumberList::contains).toList());
    solvedCount = (int) workbookProblemList.stream().filter(problemNumberList::contains).count();

    System.out.println("solvedCount = " + solvedCount);
  }
}