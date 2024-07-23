package org.example.domain.study.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "스터디 수정 요청 객체")
public record UpdateStudyRequest(
  @Schema(description = "스터디 대표 이미지 URL")
  String profileUrl,

  @Schema(description = "스터디 이름")
  String name,

  @Schema(description = "내용(에디터)")
  String content

) {

}
