package org.example.domain.inquiry_reply.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.inquiry.Inquiry;
import org.example.domain.inquiry.service.CoreInquiryService;
import org.example.domain.inquiry_reply.InquiryReply;
import org.example.domain.inquiry_reply.controller.request.CreateInquiryReplyRequest;
import org.example.domain.inquiry_reply.controller.request.UpdateInquiryReplyRequest;
import org.example.domain.inquiry_reply.repository.InquiryReplyRepository;
import org.example.domain.inquiry_reply.repository.ListInquiryReplyRepository;
import org.example.domain.member.enums.Role;
import org.example.domain.member.service.CoreMemberService;
import org.example.email.service.CoreEmailService;
import org.example.util.SecurityUtils;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateInquiryReplyService {

  private final CoreMemberService coreMemberService;
  private final CoreInquiryService coreInquiryService;
  private final CoreInquiryReplyService coreInquiryReplyService;
  private final ListInquiryReplyRepository listInquiryReplyRepository;
  private final InquiryReplyRepository inquiryReplyRepository;
  private final CoreEmailService coreEmailService;

  /**
   * 댓글 생성
   */
  public void createInquiryReply(CreateInquiryReplyRequest request) {
    if (!StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "내용이 비어있습니다.");

    // 문의 최초 답변 시 메일 발송
    Inquiry inquiry = coreInquiryService.findById(request.inquiryId());
    if (!inquiry.getSolvedYn())
    {
      inquiry.markSolved();
      coreEmailService.sendInquiryRepliedEmail(inquiry.getMember());
    }

    inquiryReplyRepository.save(
      InquiryReply.builder()
        .parentId(request.parentId() == null ? null : request.parentId())
        .inquiry(inquiry)
        .member(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()))
        .depth(request.parentId() == null ? 0 : coreInquiryReplyService.findById(request.parentId()).getDepth() + 1)
        .content(request.content())
        .build()
    );
  }

  /**
   * 댓글 수정
   */
  public void updateInquiryReply(Long replyId, UpdateInquiryReplyRequest request) {
    InquiryReply reply = coreInquiryReplyService.findById(replyId);
    if (reply.getDeleteYn())
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "삭제된 댓글은 수정할 수 없습니다.");
    if (!reply.getMember().equals(coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail())))
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "자신이 남긴 댓글 이외에는 수정할 수 없습니다.");
    if (!StringUtils.hasText(request.content()))
      throw new GeneralException(ErrorStatus.NOTICE_BAD_REQUEST, "내용이 비어있습니다.");

    reply.updateReply(request.content());
  }

  /**
   * 댓글 삭제
   */
  public void deleteInquiryReply(Long replyId) {
    InquiryReply reply = coreInquiryReplyService.findById(replyId);
    if (reply.getMember().getRole().equals(Role.ROLE_USER))
      throw new GeneralException(ErrorStatus.UNAUTHORIZED, "사용자의 댓글은 수정할 수 없습니다.");

    //***** 하위 댓글이 모두 삭제됐다면 DB에서 삭제 *****//
    boolean childDeleteYn = false;
    Pair<List<InquiryReply>, List<InquiryReply>> childrenList = getChildrenList(reply);
    // 하위에 댓글이 없는 경우
    if (childrenList.getFirst().isEmpty() && childrenList.getSecond().isEmpty()) childDeleteYn = true;
      // 하위에 삭제할 댓글이 있는 경우
    else if (!childrenList.getFirst().isEmpty()) {
      childDeleteYn = true;
      inquiryReplyRepository.deleteAll(childrenList.getFirst());
    }

    //***** 삭제된 상위 댓글에 하위 댓글이 남지 않는다면 DB에서 삭제 *****//
    boolean parentDeleteYn = false;
    List<Long> deleteReplyIdList = new ArrayList<>(Collections.singleton(reply.getId()));
    Long parentId = reply.getParentId();
    while (parentId != null && listInquiryReplyRepository.getChildrenList(parentId).size() == 1
      // 하위 댓글이 모두 삭제된 경우에만 상위 댓글 삭제
      && childDeleteYn) {
      InquiryReply parent = coreInquiryReplyService.findById(parentId);
      // 상위 댓글이 삭제되지 않았다면 삭제 X
      if (!parent.getDeleteYn()) break;

      deleteReplyIdList.add(parentId);
      parentDeleteYn = true;

      if (parent.getParentId() == null && listInquiryReplyRepository.getChildrenList(parent.getParentId()).size() == 1)
        deleteReplyIdList.add(parentId);
      parentId = parent.getParentId();
    }

    if (parentDeleteYn || childDeleteYn) inquiryReplyRepository.deleteAllById(deleteReplyIdList);
    else reply.delete();
  }

  /**
   * 삭제되지 않은 모든 하위 depth 댓글 반환
   */
  private Pair<List<InquiryReply>, List<InquiryReply>> getChildrenList(InquiryReply reply) {
    List<InquiryReply> deleteList = new ArrayList<>();
    List<InquiryReply> nonDeleteList = new ArrayList<>();

    List<InquiryReply> childrenReplyList = listInquiryReplyRepository.getChildrenList(reply.getId());
    childrenReplyList.forEach(child -> {
      // 재귀로 모든 하위 댓글 확인
      Pair<List<InquiryReply>, List<InquiryReply>> childrenList = getChildrenList(child);
      if (!childrenList.getFirst().isEmpty()) deleteList.addAll(childrenList.getFirst());
      if (!childrenList.getSecond().isEmpty()) nonDeleteList.addAll(childrenList.getSecond());

      // child 하위에 deleteYn이 false인 댓글이 없다면, 삭제할 댓글 목록에 추가
      if (child.getDeleteYn() && childrenList.getSecond().isEmpty()) deleteList.add(child);
      else nonDeleteList.add(child);
    });

    return Pair.of(deleteList, nonDeleteList);
  }
}

