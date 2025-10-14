package org.example.domain.challenge_reward.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;

@Schema(description = "챌린지 보상 사용 요청 객체")
public record UseChallengeRewardRequest(

  List<@Valid UseChallengeRewardDto> requestList
) {

}
