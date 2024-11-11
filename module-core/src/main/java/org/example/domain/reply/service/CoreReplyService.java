package org.example.domain.reply.service;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.reply.Reply;
import org.example.domain.reply.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoreReplyService {

  private final ReplyRepository replyRepository;

  public Reply findById(Long replyId) {
    return replyRepository.findById(replyId)
      .orElseThrow(() -> new GeneralException(ErrorStatus.KEY_NOT_EXIST, "존재하지 않는 댓글 ID입니다."));
  }

}
