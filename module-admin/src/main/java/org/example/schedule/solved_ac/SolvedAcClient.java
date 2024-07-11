package org.example.schedule.solved_ac;

import org.example.config.FeignClientConfig;
import org.example.schedule.solved_ac.response.algorithm.AlgorithmResponse;
import org.example.schedule.solved_ac.response.problem.ProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.svc1.name}", url = "${feign.svc1.url}", configuration = FeignClientConfig.class)
public interface SolvedAcClient {

    @GetMapping("/search/problem")
    ProblemResponse searchProblems(
        @RequestParam("page") int page,
        @RequestParam("query") String query,
        @RequestParam("sort") String sort,
        @RequestParam("direction") String direction
    );

    @GetMapping("/tag/list")
    AlgorithmResponse searchTag(
        @RequestParam("page") int page
    );
}
