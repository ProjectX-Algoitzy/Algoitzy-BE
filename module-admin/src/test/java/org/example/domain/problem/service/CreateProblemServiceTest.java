package org.example.domain.problem.service;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.example.util.http_request.Url;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CreateProblemServiceTest {

  @Autowired
  WebDriver webDriver;

  @Autowired
  Actions actions;


  @Test
  void crawlingProblem() {
    try {
      webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl("engus525"));

      LocalDate today = LocalDate.now().minusDays(7);
      LocalDate nextYear = today.plusYears(1);
      int startRect = today.getDayOfYear()
        - LocalDate.now().get(ChronoField.DAY_OF_WEEK)
        + (int) ChronoUnit.DAYS.between(today, nextYear) + 1;
      int lastRect = startRect + 6;

      List<WebElement> rectList = webDriver.findElements(By.tagName("rect"));
      int count = 0;
      for (int i = startRect; i <= lastRect; i++) {
        WebElement rect = rectList.get(i);
        actions.moveToElement(rect).perform();

        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        WebElement tooltip = webDriver.findElement(By.className("google-visualization-tooltip"));
        int colonIndex = tooltip.getText().indexOf(":");
        if (colonIndex != -1) {
          count += Integer.parseInt(tooltip.getText().substring(colonIndex + 1).strip());
        }
      }
      webDriver.quit();
    } catch (Exception e) {
    }
  }

  @Test
  void crawlingProblem2() {
    try {
      webDriver.get(Url.BAEKJOON_USER.getBaekjoonUserUrl("engus525"));

      int solvedCount = Integer.parseInt(webDriver.findElement(By.id("u-solved")).getText());
      List<String> problemNumberList = webDriver.findElements(By.cssSelector(".problem-list a"))
        .stream()
        .map(WebElement::getText)
        .limit(solvedCount)
        .toList();

      webDriver.quit();
    } catch (Exception e) {
    }
  }
}