package org.example.domain.challenge_join_log.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챌린지 참여 이력 목록 응답 객체")
public class ListChallengeJoinLogResponse {

  @Schema(description = "참여 이력 목록")
  @Default
  private List<ListChallengeJoinLogDto> joinLogList = new ArrayList<>();

  @Schema(description = "금일 데일리 챌린지 참여자 수")
  private long totalCount;
}
