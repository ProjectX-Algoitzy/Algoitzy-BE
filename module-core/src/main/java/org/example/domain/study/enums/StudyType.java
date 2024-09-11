package org.example.domain.study.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StudyType {
  REGULAR("정규"),
  TEMP("자율");

  private final String name;

}
