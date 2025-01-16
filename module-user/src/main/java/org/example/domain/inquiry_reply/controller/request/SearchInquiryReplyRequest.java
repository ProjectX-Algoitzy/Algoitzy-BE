package org.example.domain.inquiry_reply.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;

@Schema(description = "문의 댓글 조회 요청 객체")
public record SearchInquiryReplyRequest(

    @Min(1)
    @Schema(description = "페이지 번호", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int page,

    @Max(10)
    @Schema(description = "최상위 댓글 개수", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int size
) {
    public PageRequest pageRequest() { return PageRequest.of(page - 1, size);}
}