package org.example.domain.reply_like.repository;

import java.util.Optional;
import org.example.domain.member.Member;
import org.example.domain.reply.Reply;
import org.example.domain.reply_like.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {

    Optional<ReplyLike> findByMemberAndReply(Member member, Reply reply);

}
