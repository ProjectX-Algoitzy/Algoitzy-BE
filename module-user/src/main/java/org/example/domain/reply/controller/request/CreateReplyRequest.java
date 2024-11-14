package org.example.domain.reply.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "댓글 생성 요청 객체")
public record CreateReplyRequest(

    @Schema(description = "댓글이 속한 게시물")
    long boardId,

    // 자신이 부모 댓글이라면 null
    @Schema(description = "부모 댓글")
    Long parentId,

    @Schema(description = "댓글 내용")
    String content
) {

}
