package org.example.domain.answer.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.select_answer.controller.response.DetailSelectAnswerDto;
import org.example.domain.text_answer.controller.response.DetailTextAnswerDto;

@Builder(toBuilder = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "작성한 지원서 상세 응답 객체")
public class DetailAnswerResponse {

  @Schema(description = "지원서 ID")
  private Long applicationId;

  @Schema(description = "답변 ID")
  private Long answerId;

  @Schema(description = "지원서 제목")
  private String title;

  @Schema(description = "작성한 지원서 대상 스터디 이름")
  private String studyName;

  @Schema(description = "작성자 이름")
  private String submitName;

  @Schema(description = "작성자 이메일")
  private String submitEmail;

  @Schema(description = "작성자 핸드폰 번호")
  private String phoneNumber;

  @Default
  @Schema(description = "객관식 답변 list")
  private List<DetailSelectAnswerDto> selectAnswerList = new ArrayList<>();

  @Default
  @Schema(description = "주관식 답변 list")
  private List<DetailTextAnswerDto> textAnswerList = new ArrayList<>();

  @Schema(description = "제출일")
  private LocalDateTime submitTime;


}
