package org.example.domain.reply.service;

import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.Board;
import org.example.domain.board.service.CoreBoardService;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.reply.Reply;
import org.example.domain.reply.controller.request.CreateReplyRequest;
import org.example.domain.reply.controller.request.UpdateReplyRequest;
import org.example.domain.reply.repository.ReplyRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReplyService {

    private final BadWordFiltering badWordFiltering;
    private final CoreMemberService coreMemberService;
    private final CoreBoardService coreBoardService;
    private final CoreReplyService coreReplyService;
    private final ReplyRepository replyRepository;

    /**
     * 댓글 생성
     */
    public void createReply(CreateReplyRequest request) {
        if (badWordFiltering.blankCheck(request.content())) {
            throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "댓글에 욕설을 포함할 수 없습니다.");
        }

        Board board = coreBoardService.findById(request.boardId());
        Reply reply = null;
        
        // 부모 댓글 생성
        if (request.parentId() == null) {
            reply = createParentReply(request, board);
        } else { // 자식 댓글 생성
            Reply parentReply = coreReplyService.findById(request.parentId());
            validateParentReply(parentReply, request);
            reply = createChildrenReply(request, board, parentReply);
        }
        replyRepository.save(reply);
    }

    // 부모 댓글 생성
    private Reply createParentReply(CreateReplyRequest request, Board board) {
        return Reply.builder()
            .parentId(null)
            .board(board)
            .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
            .depth(0)
            .content(request.content())
            .build();
    }

    // 자식 댓글 생성
    private Reply createChildrenReply(CreateReplyRequest request, Board board, Reply parentReply) {
        return Reply.builder()
            .parentId(request.parentId())
            .board(board)
            .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
            .depth(parentReply.getDepth() + 1)
            .content(request.content())
            .build();
    }

    // 자식 댓글 오류 검출
    private void validateParentReply (Reply parentReply, CreateReplyRequest request) {
        // 부모 댓글의 게시물과 자식 댓글의 게시물이 같은지 확인
        if (!parentReply.getBoard().getId().equals(request.boardId())) {
            throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "대댓글을 남길 게시물을 확인해주세요.");
        }
    }

    /**
     * 댓글 수정
     */
    public void updateReply(Long replyId, UpdateReplyRequest request) {
        Reply reply = coreReplyService.findById(replyId);

        if (!reply.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))) {
            throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "자신이 남긴 댓글 이외에는 수정할 수 없습니다.");
        }

        if (badWordFiltering.blankCheck(request.content())) {
            throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "댓글에 욕설을 포함할 수 없습니다.");
        }

        reply.updateReply(
            request.content()
        );
    }

    /**
     * 댓글 삭제
     */
    public void deleteReply(Long replyId) {

    }
}
