package org.example.domain.comment.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "댓글 생성 요청")
public record CreateCommentRequest(

  @NotBlank
  @Schema(description = "내용")
  String content
) {

}
