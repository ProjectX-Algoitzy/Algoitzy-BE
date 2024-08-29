package org.example.util;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDateTime;

public class DateUtils {

  public static String getUpdatedTime(LocalDateTime updatedTime) {
    long minGap = MINUTES.between(updatedTime, LocalDateTime.now());
    long hourGap = HOURS.between(updatedTime, LocalDateTime.now());
    long dayGap = DAYS.between(updatedTime, LocalDateTime.now());
    if (minGap < 1) {
      return "방금 전";
    } else if (hourGap < 1) {
      return minGap + "분 전";
    } else if (dayGap < 1) {
      return hourGap + "시간 전";
    } else if (dayGap >= 365) {
      return (dayGap / 365) + "년 전";
    }

    return dayGap + "일 전";
  }
}
