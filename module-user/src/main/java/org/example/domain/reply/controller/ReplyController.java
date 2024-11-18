package org.example.domain.reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.reply.controller.request.CreateReplyRequest;
import org.example.domain.reply.controller.request.UpdateReplyRequest;
import org.example.domain.reply.service.ReplyService;
import org.example.domain.reply_like.service.ReplyLikeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Tag(name = "ReplyController", description = "[USER] 커뮤니티 댓글 관련 API")
public class ReplyController {

    private final ReplyService replyService;
    private final ReplyLikeService replyLikeService;

    @PostMapping
    @Operation(summary = "게시물 댓글/대댓글 생성")
    public ApiResponse<Void> createReply(CreateReplyRequest request) {
        replyService.createReply(request);
        return ApiResponse.onSuccess();
    }

    @PatchMapping("/{reply-id}")
    @Operation(summary = "댓글 수정")
    public ApiResponse<Void> updateReply(@PathVariable("reply-id") Long replyId, UpdateReplyRequest request) {
        replyService.updateReply(replyId, request);
        return ApiResponse.onSuccess();
    }

    @DeleteMapping("/{reply-id}")
    @Operation(summary = "댓글 삭제")
    public ApiResponse<Void> deleteReply(@PathVariable("reply-id") Long replyId) {
        replyService.deleteReply(replyId);
        return ApiResponse.onSuccess();
    }


    @PutMapping("/{reply-id}/like")
    @Operation(summary = "게시글 댓글 좋아요 ")
    public ApiResponse<Void> createReplyLike(@PathVariable("reply-id") long replyId) {
        replyLikeService.createReplyLike(replyId);
        return ApiResponse.onSuccess();
    }

}
