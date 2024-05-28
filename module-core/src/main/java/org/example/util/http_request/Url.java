package org.example.util.http_request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Url {

  BAEKJOON_USER("https://www.acmicpc.net/user/"),
  SOLVED_AC("https://solved.ac/api/v3/search/problem?query=*b..*d&page=${page}&sort=solved&direction=descending");

  private final String uri;

  public String getBaekjoonUserUrl(String handle) {
    return BAEKJOON_USER.uri + handle;
  }

  public String getSolvedAcPage(int page) {
    return SOLVED_AC.uri.replace("${page}", String.valueOf(page));
  }
}
