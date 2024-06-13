package org.example.domain.problem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.problem.service.CrawlingProblemService;
import org.example.domain.problem.service.CreateProblemService;
import org.example.schedule.solved_ac_response.ProblemDto;
import org.example.schedule.solved_ac_response.ProblemResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawling")
@Tag(name = "ProblemController", description = "[ADMIN] 백준 문제 크롤링 관련 API")
public class ProblemController {

    private final CrawlingProblemService crawlingProblemService;
    private final CreateProblemService createProblemService;

    @GetMapping("/problems")
    @Operation(summary = "백준 문제 크롤링")
    public void getProblemList() {
        List<ProblemDto> problemResponse = crawlingProblemService.searchProblems();
        createProblemService.saveProblemList(problemResponse);

    }

}
