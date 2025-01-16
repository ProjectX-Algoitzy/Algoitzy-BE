package org.example.domain.inquiry_reply.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문의 댓글 생성 요청 객체")
public record CreateInquiryReplyRequest(

    @Schema(description = "문의 ID")
    long inquiryId,

    @Schema(description = "부모 댓글")
    Long parentId,

    @Schema(description = "댓글 내용")
    String content
) {

}
