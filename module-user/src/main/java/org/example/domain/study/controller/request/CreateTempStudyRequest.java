package org.example.domain.study.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(description = "자율 스터디 생성 요청 객체")
public record CreateTempStudyRequest(

  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl,

  @NotBlank
  @Schema(description = "스터디 이름")
  String name,

  @NotBlank
  @Schema(description = "내용(에디터)")
  String content,

  @Min(0)
  @Schema(description = "모집 인원")
  Integer memberLimit,

  @NotBlank
  @Schema(description = "스터디 주제")
  String subject,

  @NotBlank
  @Schema(description = "스터디 대상")
  String target,

  @NotBlank
  @Schema(description = "진행 방식")
  String way

) {

  @Builder
  public CreateTempStudyRequest {
    if (memberLimit == null) {
      memberLimit = Integer.MAX_VALUE;
    }
  }

}
