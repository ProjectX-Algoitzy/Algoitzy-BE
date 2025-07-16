package org.example.domain.challenge_reward_log.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챌린지 보상 이력 목록 응답 객체")
public class ListChallengeRewardLogResponse {

  @Schema(description = "보상 이력 목록")
  private List<ListChallengeRewardLogDto> rewardLogList;

  @Schema(description = "총 이력 수")
  private long totalCount;

}
