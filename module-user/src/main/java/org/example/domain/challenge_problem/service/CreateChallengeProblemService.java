package org.example.domain.challenge_problem.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.challenge_problem.ChallengeProblem;
import org.example.domain.challenge_problem.repository.ChallengeProblemRepository;
import org.example.domain.member.Member;
import org.example.domain.member.repository.ListMemberRepository;
import org.example.domain.problem.Problem;
import org.example.domain.problem.repository.ProblemRepository;
import org.example.domain.workbook.service.CreateWorkbookService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChallengeProblemService {

  private final CreateWorkbookService createWorkbookService;

  private final ListMemberRepository listMemberRepository;
  private final ChallengeProblemRepository challengeProblemRepository;
  private final ProblemRepository problemRepository;

  public void createChallengeProblem() {
    if (challengeProblemRepository.existsById(LocalDate.now())) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "데일리 챌린지 문제가 이미 선정되었습니다.");
    }

    List<Member> memberList = listMemberRepository.getChallengeMemberList();
    final int MAX_MEMBER_PER_QUERY = 10;
    int queryCount = (int) Math.ceil((double) memberList.size() / MAX_MEMBER_PER_QUERY);

    // 난이도 설정 쿼리
    String silverQuery = "*s4..s1 ";
    String goldQuery = "*g5..*g4 ";

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

    List<Integer> result = createWorkbookService.getProblemListFromSolvedAc(queryCount, silverQuery, goldQuery, queryList);
    Collections.shuffle(result);
    Problem problem = problemRepository.findById(result.get(0))
      .orElseThrow(() -> new GeneralException(ErrorStatus.INTERNAL_ERROR, "조건을 만족하는 문제를 찾지 못했습니다."));

    challengeProblemRepository.save(
      ChallengeProblem.builder()
        .problem(problem)
        .build()
    );
  }
}
