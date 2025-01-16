package org.example.domain.inquiry_reply.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.example.domain.inquiry_reply.controller.request.SearchInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.response.ListInquiryReplyDto;
import org.example.domain.inquiry_reply.controller.response.ListInquiryReplyResponse;
import org.example.domain.inquiry_reply.repository.ListInquiryReplyRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListInquiryReplyService {

  private final ListInquiryReplyRepository listInquiryReplyRepository;


  /**
   * 문의 댓글 목록 조회
   */
  public ListInquiryReplyResponse getInquiryReplyList(Long inquiryId, SearchInquiryReplyRequest request) {

    // 부모(최상위) 댓글 조회
    Page<ListInquiryReplyDto> parentReplyList = listInquiryReplyRepository.getParentReplyList(inquiryId, request);

    // 자식(depth 1 이상) 댓글 조회
    List<ListInquiryReplyDto> childrenReplyList = listInquiryReplyRepository.getChildrenReplyList(inquiryId);

    // Map에 부모/자식 댓글 Id로 저장
    Map<Long, ListInquiryReplyDto> map = new HashMap<>();
    List<ListInquiryReplyDto> replyList = new ArrayList<>(parentReplyList.getContent());
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

    return ListInquiryReplyResponse.builder()
      .replyList(replyList)
      .parentReplyCount(parentReplyList.getTotalElements())
      .build();
  }

}
