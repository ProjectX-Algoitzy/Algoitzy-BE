package org.example.domain.reply.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.domain.reply.controller.request.SearchReplyRequest;
import org.example.domain.reply.controller.response.ListReplyDto;
import org.example.domain.reply.controller.response.ListReplyResponse;
import org.example.domain.reply.repository.ListReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListReplyService {

  private final ListReplyRepository listReplyRepository;


  /**
   * 게시글 댓글 목록 조회
   */
  public ListReplyResponse getReplyList(Long boardId, SearchReplyRequest request) {

    // 부모(최상위) 댓글 조회
    Page<ListReplyDto> parentReplyList = listReplyRepository.getParentReplyList(boardId, request);

    // 자식(depth 1 이상) 댓글 조회
    List<ListReplyDto> childrenReplyList = listReplyRepository.getChildrenReplyList(boardId);

    // Map에 부모/자식 댓글 Id로 저장
    Map<Long, ListReplyDto> map = new HashMap<>();
    List<ListReplyDto> replyList = new ArrayList<>(parentReplyList.getContent());
    replyList.forEach(reply -> map.put(reply.getReplyId(), reply));

    childrenReplyList.forEach(reply -> {
      map.put(reply.getReplyId(), reply);

      // 부모 댓글이 존재하면 부모 댓글의 하위 댓글 목록에 추가
      if (map.get(reply.getParentReplyId()) != null) {
        map.get(reply.getParentReplyId())
          .getChildrenReplyList()
          .add(reply);
      }
    });

    return ListReplyResponse.builder()
      .replyList(replyList)
      .build();
  }

}
