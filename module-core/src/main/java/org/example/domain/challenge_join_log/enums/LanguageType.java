package org.example.domain.challenge_join_log.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LanguageType {
  CPP(1000),
  PYTHON(1001),
  JAVA(1002);

  private final int baekjoonCode;
}
