package org.example.domain.challenge_problem.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.domain.member.Member;
import org.example.domain.member.repository.ListMemberRepository;
import org.example.domain.workbook.service.CreateWorkbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class CreateChallengeProblemServiceTest {

  @Autowired
  CreateWorkbookService createWorkbookService;
  @Autowired
  private ListMemberRepository listMemberRepository;


  @Test
  void 데일리_챌린지_문제_선정() {
    List<Member> memberList = listMemberRepository.getChallengeMemberList();
    final int MAX_MEMBER_PER_QUERY = 10;
    int queryCount = (int) Math.ceil((double) memberList.size() / MAX_MEMBER_PER_QUERY);

    // 난이도 설정 쿼리
    String silverQuery = "*g5..g1 ";
    String goldQuery = "*g5..g1 ";

    // 스터디원이 푼 문제 제외 쿼리
    List<StringBuilder> queryList = new ArrayList<>();
    for (int count = 0; count < queryCount; count++) {
      queryList.add(new StringBuilder());
      for (int i = 0; i < MAX_MEMBER_PER_QUERY; i++) {
        int idx = count * MAX_MEMBER_PER_QUERY + i;
        if (idx >= memberList.size()) break;

        queryList.get(count).append(" -s@").append(memberList.get(idx).getHandle());
      }
    }

    System.out.println("queryList = " + queryList);
    List<Integer> result = createWorkbookService.getProblemListFromSolvedAc(queryCount, silverQuery, goldQuery, queryList);
    Collections.shuffle(result);
    System.out.println("result = " + result);
  }
}