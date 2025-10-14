package org.example.domain.attendance.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttendanceType {

  PROBLEM("문제 인증"),
  BLOG("블로그 포스팅"),
  WORKBOOK("주말 모의테스트");

  private final String value;
}
