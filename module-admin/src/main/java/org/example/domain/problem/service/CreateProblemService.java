package org.example.domain.problem.service;

import static org.example.util.http_request.Url.SOLVED_AC;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.problem_algorithm.repository.ProblemAlgorithmRepository;
import org.example.util.http_request.HttpRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateProblemService {

  private final ProblemRepository problemRepository;
  private final ProblemAlgorithmRepository problemAlgorithmRepository;

  /**
   * 백준 문제 크롤링
   */
  public void crawlingProblem() {
    // todo sebin : test code 통해 작업

    // 1. solvedac에서 문제 list 크롤링 -> 반복문으로 페이지에 문제 없을때까지 && 이미 저장한 페이지 이후부터
    String jsonString = HttpRequest.getRequest(SOLVED_AC.getSolvedAcPage(1), String.class).getBody();
    JsonObject jsonObject = JsonParser.parseString(Objects.requireNonNull(jsonString)).getAsJsonObject();
    JsonArray problemArray = jsonObject.getAsJsonArray("items");
    log.info("problemArray = " + problemArray);

    // 2. DB problem 테이블에 저장 -> 백준 문제 번호, 문제 이름, 난이도(json에서 level을 통해 Level enum 으로 변환 후 저장) 저장

    // 3. DB problem_algorithm 테이블에 알고리즘 유형 저장 -> json에서 tags 참조

    // 4. 크롤링했던 마지막 페이지를 저장하기 위한 테이블과 갱신 로직 필요

  }
}
