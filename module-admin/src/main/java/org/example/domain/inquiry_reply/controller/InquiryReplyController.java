package org.example.domain.inquiry_reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.inquiry_reply.controller.request.CreateInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.request.UpdateInquiryReplyRequest;
import org.example.domain.inquiry_reply.service.InquiryReplyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inquiry-reply")
@RequiredArgsConstructor
@Tag(name = "InquiryReplyController", description = "[ADMIN] 문의 댓글 관련 API")
public class InquiryReplyController {

    private final InquiryReplyService inquiryReplyService;

    @PostMapping
    @Operation(summary = "문의 댓글/대댓글 생성")
    public ApiResponse<Void> createInquiryReply(
      @RequestBody @Valid CreateInquiryReplyRequest request) {
        inquiryReplyService.createInquiryReply(request);
        return ApiResponse.onSuccess();
    }

    @PatchMapping("/{reply-id}")
    @Operation(summary = "댓글 수정")
    public ApiResponse<Void> updateInquiryReply(
      @PathVariable("reply-id") Long replyId,
      @RequestBody @Valid UpdateInquiryReplyRequest request) {
        inquiryReplyService.updateInquiryReply(replyId, request);
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("/{reply-id}")
    @Operation(summary = "댓글 삭제")
    public ApiResponse<Void> deleteInquiryReply(@PathVariable("reply-id") Long replyId) {
        inquiryReplyService.deleteInquiryReply(replyId);
        return ApiResponse.onSuccess();
    }

}
