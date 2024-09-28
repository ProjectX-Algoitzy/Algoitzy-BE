package org.example.domain.reply;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bouncycastle.pqc.crypto.util.PQCOtherInfoGenerator.PartyU;
import org.example.domain.board.Board;
import org.example.domain.member.Member;
import org.example.domain.reply_like.ReplyLike;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Comment("댓글/대댓글의 깊이")
    private Integer depth;

    @Comment("댓글 순서")
    private Integer orderNumber;

    @Comment("부모 댓글 참조 id")
    private Integer parent_reply_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_reply_id")
    private Reply parent;

    @OneToMany(mappedBy = "parent")
    private List<Reply> childrenList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime updatedTime;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    @Builder
    public Reply(Long id, String content, Integer depth, Integer orderNumber,
        Integer parent_reply_id,
        Reply parent, List<Reply> childrenList, Member member, Board board,
        List<ReplyLike> replyLikeList) {
        this.id = id;
        this.content = content;
        this.depth = depth;
        this.orderNumber = orderNumber;
        this.parent_reply_id = parent_reply_id;
        this.parent = parent;
        this.childrenList = childrenList;
        this.member = member;
        this.board = board;
        this.replyLikeList = replyLikeList;
    }
}
