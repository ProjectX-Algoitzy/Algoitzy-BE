package org.example.domain.challenge_reward.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
class CreateChallengeRewardServiceTest {

  @Autowired
  CreateChallengeRewardService createChallengeRewardService;


  @Test
  void 챌린지_정산() {
    createChallengeRewardService.createChallengeReward();
  }
}