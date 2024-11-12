package org.example.domain.reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.reply.controller.request.CreateReplyRequest;
import org.example.domain.reply.service.ReplyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Tag(name = "ReplyController", description = "[USER] 커뮤니티 댓글 관련 API")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping
    @Operation(summary = "게시물 댓글/대댓글 생성")
    public ApiResponse<Void> createReply(CreateReplyRequest request) {
        replyService.createReply(request);
        return ApiResponse.onSuccess();
    }
}
