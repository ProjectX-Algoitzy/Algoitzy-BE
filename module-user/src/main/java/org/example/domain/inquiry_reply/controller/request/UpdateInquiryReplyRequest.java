package org.example.domain.inquiry_reply.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문의 댓글 수정 요청 객체")
public record UpdateInquiryReplyRequest(

    @Schema(description = "댓글 내용")
    String content

) {

}
