package org.example.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 수정 요청")
public record UpdateCommentRequest(

  @Schema(description = "내용")
  String content
) {

}
