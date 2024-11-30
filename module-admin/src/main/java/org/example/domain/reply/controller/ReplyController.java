package org.example.domain.reply.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.reply.service.ReplyService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
@Tag(name = "ReplyController", description = "[ADMIN] 댓글 관련 API")
public class ReplyController {

  private final ReplyService replyService;

  @DeleteMapping("/{reply-id}")
  @Operation(summary = "댓글 삭제")
  public ApiResponse<Void> deleteReply(@PathVariable("reply-id") Long replyId) {
    replyService.deleteReply(replyId);
    return ApiResponse.onSuccess();
  }

}
