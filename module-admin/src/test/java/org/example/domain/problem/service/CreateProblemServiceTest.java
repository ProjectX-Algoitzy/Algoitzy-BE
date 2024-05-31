package org.example.domain.problem.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class CreateProblemServiceTest {

  @Autowired CreateProblemService createProblemService;

  @Test
  void crawlingProblem() {
    createProblemService.crawlingProblem();
  }
}