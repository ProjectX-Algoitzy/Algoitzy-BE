package org.example.domain.challenge_join_log.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.challenge_join_log.enums.LanguageType;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "챌린지 참여 이력 목록 응답 DTO")
public class ListChallengeJoinLogDto {

  @Schema(description = "챌린지 날짜")
  private LocalDate date;

  @Setter
  @Schema(description = "순위")
  private int rank;

  @Schema(description = "언어")
  private LanguageType languageType;

  @JsonIgnore
  @Schema(description = "이메일")
  private String email;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "실행 시간")
  private String executionTime;

  @Schema(description = "메모리")
  private String memory;

  @Schema(description = "코드 길이")
  private String codeLength;
}
