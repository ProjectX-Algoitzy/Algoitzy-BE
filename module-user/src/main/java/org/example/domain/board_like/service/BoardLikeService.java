package org.example.domain.board_like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final CreateBoardLikeService createBoardLikeService;

    /**
     * 게시글 좋아요
     */
    public void createBoardLike(long boardId) {
        createBoardLikeService.createBoardLike(boardId);
    }
}
