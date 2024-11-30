package org.example.domain.reply.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateReplyRequest(

    @Schema(description = "댓글 내용")
    String content

) {

}
