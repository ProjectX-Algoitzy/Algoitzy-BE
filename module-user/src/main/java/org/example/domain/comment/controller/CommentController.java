package org.example.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.api_response.ApiResponse;
import org.example.domain.comment.controller.request.UpdateCommentRequest;
import org.example.domain.comment.service.CommentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "CommentController", description = "[USER] 게시판 댓글 관련 API")
public class CommentController {

  private final CommentService commentService;

  @PatchMapping("/{comment-id}")
  @Operation(summary = "댓글 수정")
  public ApiResponse<Void> updateComment(
    @PathVariable("comment-id") Long commentId,
    @RequestBody @Valid UpdateCommentRequest request) {
    commentService.updateComment(commentId, request);
    return ApiResponse.onCreate();
  }

  @DeleteMapping("/{comment-id}")
  @Operation(summary = "댓글 삭제")
  public ApiResponse<Void> deleteComment(@PathVariable("comment-id") Long commentId) {
    commentService.deleteComment(commentId);
    return ApiResponse.onSuccess();
  }
}
