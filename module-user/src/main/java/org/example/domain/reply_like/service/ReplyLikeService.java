package org.example.domain.reply_like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyLikeService {

    private final CreateReplyLikeService createReplyLikeService;

    /**
     * 댓글 좋아요
     */
    public void createReplyLike(long replyId) {
        createReplyLikeService.createReplyLike(replyId);
    }


}
