package org.example.domain.problem.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.problem.Problem;
import org.example.schedule.solved_ac_response.ProblemDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final CrawlingProblemService crawlingProblemService;
    private final CreateProblemService createProblemService;

    /*
    * 백준 문제 크롤링 -> api 요청용
    * */
    public void searchProblems() {
        crawlingProblemService.searchProblems();
    }

    /*
    * 백준 문제 크롤링 후, Problem 테이블 저장
    * */
    public void saveProblemList(List<ProblemDto> problemDtoList) {
        createProblemService.saveProblemList(problemDtoList);
    }


}
