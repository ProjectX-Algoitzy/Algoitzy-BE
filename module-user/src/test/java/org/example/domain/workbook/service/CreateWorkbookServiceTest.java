package org.example.domain.workbook.service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.example.schedule.solved_ac.SolvedAcClient;
import org.example.schedule.solved_ac.response.problem.ProblemDto;
import org.example.schedule.solved_ac.response.problem.ProblemResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
public class CreateWorkbookServiceTest {

  @Autowired
  SolvedAcClient solvedAcClient;
  private static final String SORT = "solved";
  private static final String DIRECTION = "desc";



  @Test
  void 정규스터디_문제집생성() {

    int week = 7;

    String[] handle =
      {"engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525",
        "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525",
        "engus525", "engus525", "engus525", "engus525", "engus525", "engus525", "engus525"};

    final int MAX_MEMBER_PER_QUERY = 10;
    int queryCount = (int) Math.ceil((double) handle.length / MAX_MEMBER_PER_QUERY);

    // 난이도 설정 쿼리
    String silverQuery = "*s4..s1 ";
    String goldQuery = "*g ";

    // 주차별 알고리즘 유형 설정 쿼리
    String algorithmQuery = getAlgorithmQuery(week);

    // 스터디원이 푼 문제 제외 쿼리
    List<StringBuilder> queryList = new ArrayList<>();
    for (int count = 0; count < queryCount; count++) {
      queryList.add(new StringBuilder(algorithmQuery));
      for (int i = 0; i < MAX_MEMBER_PER_QUERY; i++) {
        int idx = count * MAX_MEMBER_PER_QUERY + i;
        if (idx >= handle.length) break;

        queryList.get(count).append(" -s@").append(handle[idx]);
      }
    }

    // solved.ac 요청
    List<Set<Integer>> silverProblemSetList = new ArrayList<>();
    List<Set<Integer>> goldProblemSetList = new ArrayList<>();
    for (int count = 0; count < queryCount; count++) {

      System.out.println("silverQuery + queryList.get(count) = " + silverQuery + queryList.get(count));
      System.out.println("goldQuery + queryList.get(count) = " + goldQuery + queryList.get(count));
      ProblemResponse silverProblemResponsePage1 = solvedAcClient.searchProblems(1, silverQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse silverProblemResponsePage2 = solvedAcClient.searchProblems(2, silverQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse goldProblemResponsePage1 = solvedAcClient.searchProblems(1, goldQuery + queryList.get(count), SORT, DIRECTION);
      ProblemResponse goldProblemResponsePage2 = solvedAcClient.searchProblems(2, goldQuery + queryList.get(count), SORT, DIRECTION);

      System.out.println("goldProblemResponsePage1.getProblemList() = " + goldProblemResponsePage1.getProblemList());
      System.out.println("goldProblemResponsePage2.getProblemList() = " + goldProblemResponsePage2.getProblemList());
      Set<Integer> silverPage1 = silverProblemResponsePage1.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> silverPage2 = silverProblemResponsePage2.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> goldPage1 = goldProblemResponsePage1.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));
      Set<Integer> goldPage2 = goldProblemResponsePage2.getProblemList().stream().map(ProblemDto::getNumber)
        .collect(Collectors.toCollection(LinkedHashSet::new));

      Set<Integer> silverProblemSet = new LinkedHashSet<>();
      silverProblemSet.addAll(silverPage1);
      silverProblemSet.addAll(silverPage2);
      System.out.println("silverProblemSet = " + silverProblemSet);
      silverProblemSetList.add(silverProblemSet);

      Set<Integer> goldProblemSet = new LinkedHashSet<>();
      goldProblemSet.addAll(goldPage1);
      goldProblemSet.addAll(goldPage2);
      goldProblemSetList.add(goldProblemSet);
    }

    List<Integer> result = new ArrayList<>();
    if (!silverProblemSetList.isEmpty()) {
      Set<Integer> silverResultSet = new LinkedHashSet<>(silverProblemSetList.get(0));
      for (int i = 1; i < silverProblemSetList.size(); i++) {
        silverResultSet.retainAll(silverProblemSetList.get(i));
      }
      System.out.println("silverResultSet = " + silverResultSet);
      result.addAll(silverResultSet.stream().limit(3).toList());
    }

    if (!goldProblemSetList.isEmpty()) {
      Set<Integer> goldResultSet = new LinkedHashSet<>(goldProblemSetList.get(0));
      for (int i = 1; i < goldProblemSetList.size(); i++) {
        goldResultSet.retainAll(goldProblemSetList.get(i));
      }
      result.addAll(goldResultSet.stream().limit(3).toList());
    }

    System.out.println(result);

  }

  private String getAlgorithmQuery(int week) {
    String algorithmQuery = "";
    switch (week) {
      case 1 -> algorithmQuery = "(#bruteforcing | #backtracking) -#dfs -#bfs";
      case 2 -> algorithmQuery = "#dp";
      case 3 -> algorithmQuery = "(#simulation | #two_pointer)";
      case 4 -> algorithmQuery = "(#binary_search | #prefix_sum)";
      case 5 -> algorithmQuery = "#data_structures";
      case 6 -> algorithmQuery = "(#bfs | #dfs)";
      case 7 -> algorithmQuery = "#dijkstra";
      case 8 -> algorithmQuery = "#greedy";
    }
    return algorithmQuery;
  }


}