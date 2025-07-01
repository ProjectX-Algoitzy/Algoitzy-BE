package org.example.domain.challenge_reward.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.example.domain.attendance.enums.AttendanceType;

@Schema(description = "챌린지 보상 사용 요청 객체")
public record UseChallengeRewardDto(

  @NotNull
  @Schema(description = "출석부 ID")
  Long attendanceId,

  @Schema(description = "출석 유형")
  AttendanceType attendanceType
) {

}
