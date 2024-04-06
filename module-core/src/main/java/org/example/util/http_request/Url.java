package org.example.util.http_request;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Url {

  BAEKJOON_USER("https://www.acmicpc.net/user/");

  private final String baekjoonUser;

  public String getBaekjoonUserUrl(String handle) {
    return BAEKJOON_USER.baekjoonUser + handle;
  }
}
