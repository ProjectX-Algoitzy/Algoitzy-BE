package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.study_member.enums.StudyMemberStatus;

@Getter
@NoArgsConstructor
@Schema(description = "작성한 지원서 목록 응답 객체")
public class ListAnswerDto {

  @Schema(description = "답변 ID")
  private Long answerId;

  @Schema(description = "작성자 이름")
  private String submitName;

  @Schema(description = "작성자 이메일")
  private String submitEmail;

  @Schema(description = "작성자 학년")
  private int grade;

  @Schema(description = "작성자 학과")
  private String major;

  @Schema(description = "작성한 지원서 대상 스터디 이름")
  private String studyName;

  @Schema(description = "전형 단계")
  private String status;

  @Schema(description = "면접 ID")
  private long interviewId;

  @Schema(description = "면접 일자")
  private String interviewTime;

  @Schema(description = "제출일")
  private LocalDateTime submitTime;

  public void updateStatus(String name) {
    this.status = StudyMemberStatus.valueOf(name).getStatus();
  }
}
