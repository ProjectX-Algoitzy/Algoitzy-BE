package org.example.util;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDateTime;
import java.util.Date;

public class DateUtils {

  public final static Date ONE_HOUR = new Date((new Date()).getTime() + 3600000);
  public final static Date ONE_DAY = new Date((new Date()).getTime() + 86400000);

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
