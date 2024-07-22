package org.example.domain.workbook.enums;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CodingTestBasicWorkbook {

  WEEK1(1, List.of(4435, 2869, 23739, 3135, 9996)),
  WEEK2(2, List.of(17388, 14425, 1475, 4673, 18111)),
  WEEK3(3, List.of(1436, 1813, 2153, 7785, 18111)),
  WEEK4(4, List.of(8611, 9012, 1935, 2812, 2630)),
  WEEK5(5, List.of(11728, 15820, 15821, 1946, 16935)),
  WEEK6(6, List.of(2744, 10972, 1759, 5430)),
  WEEK7(7, List.of(12840, 11866, 8989, 2630)),
  WEEK8(8, List.of(2164, 17298, 5464, 11729));

  public final int week;
  public final List<Integer> problemNumberList;

  public static CodingTestBasicWorkbook findByWeek(int week) {
    return Arrays.stream(values())
      .filter(value -> value.week == week)
      .findFirst()
      .orElseThrow();
  }
}
