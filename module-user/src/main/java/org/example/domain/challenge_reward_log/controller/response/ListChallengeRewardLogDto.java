package org.example.domain.challenge_reward_log.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.attendance.enums.AttendanceType;
import org.example.domain.challenge_reward_log.enums.ChallengeRewardLogType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챌린지 보상 이력 목록 응답 DTO")
public class ListChallengeRewardLogDto {

  @Schema(description = "보상 이력 ID")
  private long logId;

  @Schema(description = "보상 이력 유형")
  private String logType;

  @Setter
  @Schema(description = "내용")
  private String content;

  @Default
  @Schema(description = "1등한 문제 목록")
  private List<Integer> problemList = new ArrayList<>();

  @JsonIgnore
  private String studyName;

  @JsonIgnore
  private Integer generation;

  @JsonIgnore
  private Integer week;

  @JsonIgnore
  private AttendanceType attendanceType;

  @Schema(description = "획득/사용일")
  private LocalDateTime logDate;

  @Schema(description = "총합")
  private long rewardCount;


  public void updateLogType() {
    this.logType = ChallengeRewardLogType.valueOf(this.logType).getType();
  }
}
