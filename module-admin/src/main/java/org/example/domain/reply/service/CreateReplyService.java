package org.example.domain.reply.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.reply.Reply;
import org.example.domain.reply.repository.ListReplyRepository;
import org.example.domain.reply.repository.ReplyRepository;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateReplyService {

  private final CoreReplyService coreReplyService;
  private final ListReplyRepository listReplyRepository;
  private final ReplyRepository replyRepository;

  /**
   * 댓글 삭제
   */
  public void deleteReply(Long replyId) {
    Reply reply = coreReplyService.findById(replyId);
    if (reply.getDeleteYn()) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "이미 삭제된 댓글입니다.");
    }

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
