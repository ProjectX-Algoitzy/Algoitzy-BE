package org.example.util;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDateTime;

public class ValueUtils {

  public static final String INTERVIEW_QUESTION = "가능한 면접 일자를 선택해주세요.";

  public static String getUpdatedTime(LocalDateTime updatedTime) {
    long hourGap = HOURS.between(updatedTime, LocalDateTime.now());
    long dayGap = DAYS.between(updatedTime, LocalDateTime.now());
    if (hourGap < 1) {
      return MINUTES.between(updatedTime, LocalDateTime.now()) + "분 전";
    } else if (dayGap < 1) {
      return hourGap + "시간 전";
    } else if (dayGap >= 365) {
      return (dayGap / 365) + "년 전";
    }

    return dayGap + "일 전";
  }
}
