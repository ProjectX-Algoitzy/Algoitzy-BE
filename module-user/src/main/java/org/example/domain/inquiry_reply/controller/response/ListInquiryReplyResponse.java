package org.example.domain.inquiry_reply.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "문의 댓글 목록 조회 응답 객체")
public class ListInquiryReplyResponse {

    @Schema(description = "댓글 목록")
    private List<ListInquiryReplyDto> replyList;

    @Schema(description = "최상위 댓글 수")
    private long parentReplyCount;
}
