package org.example.util.http_request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.domain.challenge_join_log.enums.LanguageType;

@Getter
@RequiredArgsConstructor
public enum Url {

  BAEKJOON_PROBLEM("https://www.acmicpc.net/problem/"),
  BAEKJOON_USER("https://www.acmicpc.net/user/"),
  BAEKJOON_STATUS("https://www.acmicpc.net/status?");


  private final String uri;

  public String getBaekjoonUserUrl(String handle) {
    return BAEKJOON_USER.uri + handle;
  }

  public String getBaekjoonStatusUrl(Integer problemNumber, String handle, LanguageType languageType) {
    return BAEKJOON_STATUS.uri
      + "problem_id" + "=" + problemNumber + "&"
      + "user_id" + "=" + handle + "&"
      + "language_id" + "=" + languageType.getBaekjoonCode() + "&"
      + "result_id" + "=" + 4; // 맞은 문제만
  }

}
