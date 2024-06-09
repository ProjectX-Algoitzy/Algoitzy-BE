package org.example.schedule;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.cloud.openfeign.FeignClient(name = "solved.ac-problems-api", url = "https://solved.ac/api/v3/search/problem", configuration = SolvedAcProblemSearchClient.class)
public interface SolvedAcProblemSearchClient {

    @GetMapping
    Object searchProblems(
        @RequestParam("query") String query,
        @RequestParam("page") int page,
        @RequestParam("sort") String sort,
        @RequestParam("direction") String direction
    );
}
