package org.example.util.http_request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Url {

  BAEKJOON_PROBLEM("https://www.acmicpc.net/problem/"),
  BAEKJOON_USER("https://www.acmicpc.net/user/");

  private final String uri;

  public String getBaekjoonUserUrl(String handle) {
    return BAEKJOON_USER.uri + handle;
  }

}
