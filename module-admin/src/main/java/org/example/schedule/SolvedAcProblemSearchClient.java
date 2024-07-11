package org.example.schedule;

import org.example.config.FeignClientConfig;
import org.example.schedule.solved_ac_response.ProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${feign.svc1.name}", url = "${feign.svc1.url}", configuration = FeignClientConfig.class)
public interface SolvedAcProblemSearchClient {

    @GetMapping()
    ProblemResponse searchProblems(
        @RequestParam("page") int page,
        @RequestParam("query") String query,
        @RequestParam("sort") String sort,
        @RequestParam("direction") String direction
    );
}
