package org.example.domain.reply_like.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.Member;
import org.example.domain.member.service.CoreMemberService;
import org.example.domain.reply.Reply;
import org.example.domain.reply.repository.ReplyRepository;
import org.example.domain.reply.service.CoreReplyService;
import org.example.domain.reply_like.ReplyLike;
import org.example.domain.reply_like.repository.ReplyLikeRepository;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateReplyLikeService {

    private final CoreReplyService coreReplyService;
    private final CoreMemberService coreMemberService;
    private final ReplyLikeRepository replyLikeRepository;
    private final ReplyRepository replyRepository;

    public void createReplyLike(long replyId) {
        Reply reply = coreReplyService.findById(replyId);
        Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());

        // 사용자가 해당 댓글에 좋아요를 눌렀는지 확인
        ReplyLike isReplyLiked = replyLikeRepository.findByMemberAndReply(member, reply).orElse(null);

        if (isReplyLiked != null) {
            // 이미 좋아요가 눌러져 있으면 삭제
            replyLikeRepository.delete(isReplyLiked);
            reply.deleteReplyLike(isReplyLiked);
        } else {
            // 좋아요가 눌러져 있지 않으면 생성
            ReplyLike replyLike = ReplyLike.builder()
                .member(member)
                .reply(reply)
                .build();

            replyLikeRepository.save(replyLike);
            reply.createReplyLike(replyLike);
        }
        replyRepository.save(reply);
    }
}
