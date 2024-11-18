package org.example.domain.reply.service;

import com.vane.badwordfiltering.BadWordFiltering;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.board.Board;
import org.example.domain.board.service.CoreBoardService;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.reply.Reply;
import org.example.domain.reply.controller.request.CreateReplyRequest;
import org.example.domain.reply.controller.request.UpdateReplyRequest;
import org.example.domain.reply.repository.ListReplyRepository;
import org.example.domain.reply.repository.ReplyRepository;
import org.example.util.SecurityUtils;
import org.springframework.data.util.Pair;
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
    private final ListReplyRepository listReplyRepository;


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
        Reply reply = coreReplyService.findById(replyId);

        //***** 하위 댓글이 모두 삭제됐다면 DB에서 삭제 *****//
        boolean childDeleteYn = false;
        Pair<List<Reply>, List<Reply>> childrenList = getChildrenList(reply);
        // 하위에 댓글이 없는 경우
        if (childrenList.getFirst().isEmpty() && childrenList.getSecond().isEmpty()) childDeleteYn = true;
            // 하위에 삭제할 댓글이 있는 경우
        else if (!childrenList.getFirst().isEmpty()) {
            childDeleteYn = true;
            replyRepository.deleteAll(childrenList.getFirst());
        }

        //***** 삭제된 상위 댓글에 하위 댓글이 남지 않는다면 DB에서 삭제 *****//
        boolean parentDeleteYn = false;
        List<Long> deleteReplyIdList = new ArrayList<>(Collections.singleton(reply.getId()));
        Long parentId = reply.getParentId();
        while (parentId != null && listReplyRepository.getChildrenList(parentId).size() == 1
            // 하위 댓글이 모두 삭제된 경우에만 상위 댓글 삭제
            && childDeleteYn) {
            Reply parent = coreReplyService.findById(parentId);
            // 상위 댓글이 삭제되지 않았다면 삭제 X
            if (!parent.getDeleteYn()) break;

            deleteReplyIdList.add(parentId);
            parentDeleteYn = true;

            if (parent.getParentId() == null && listReplyRepository.getChildrenList(parent.getParentId()).size() == 1) deleteReplyIdList.add(parentId);
            parentId = parent.getParentId();
        }

        if (parentDeleteYn || childDeleteYn) replyRepository.deleteAllById(deleteReplyIdList);
        else reply.deleteByAdmin();
    }

    /**
     * 삭제되지 않은 모든 하위 depth 댓글 반환
     */
    private Pair<List<Reply>, List<Reply>> getChildrenList(Reply reply) {
        List<Reply> deleteList = new ArrayList<>();
        List<Reply> nonDeleteList = new ArrayList<>();

        List<Reply> childrenReplyList = listReplyRepository.getChildrenList(reply.getId());
        childrenReplyList.forEach(child -> {
            // 재귀로 모든 하위 댓글 확인
            Pair<List<Reply>, List<Reply>> childrenList = getChildrenList(child);
            if (!childrenList.getFirst().isEmpty()) deleteList.addAll(childrenList.getFirst());
            if (!childrenList.getSecond().isEmpty()) nonDeleteList.addAll(childrenList.getSecond());

            // child 하위에 deleteYn이 false인 댓글이 없다면, 삭제할 댓글 목록에 추가
            if (child.getDeleteYn() && childrenList.getSecond().isEmpty()) deleteList.add(child);
            else nonDeleteList.add(child);
        });

        return Pair.of(deleteList, nonDeleteList);
    }
}

