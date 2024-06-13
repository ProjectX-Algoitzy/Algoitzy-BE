package org.example.domain.problem.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.schedule.SolvedAcProblemSearchClient;
import org.example.schedule.solved_ac_response.ProblemDto;
import org.example.schedule.solved_ac_response.ProblemResponse;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
// 1. solvedac에서 문제 list 크롤링 -> solvedac unofficial API 요청
public class CrawlingProblemService {

    private final SolvedAcProblemSearchClient solvedAcProblemSearchClient;

    public List<ProblemDto> searchProblems() {

        int page = 1;
        String query = "*b..*d";
        String sort = "id";
        String direction = "asc";

        ProblemResponse problemResponse = solvedAcProblemSearchClient.searchProblems(page, query, sort, direction);
        return problemResponse.getProblemList();
    }
}
