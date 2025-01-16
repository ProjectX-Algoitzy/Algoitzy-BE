package org.example.domain.inquiry_reply;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.inquiry.Inquiry;
import org.example.domain.member.Member;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.StringUtils;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class InquiryReply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("내용")
  @Column(length = 1000)
  private String content;

  @Comment("댓글/대댓글의 깊이")
  private Integer depth;

  @Comment("부모 댓글 참조 id")
  private Long parentId;

  @Convert(converter = BooleanToYNConverter.class)
  @Comment("삭제 여부")
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  private Boolean deleteYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "inquiry_id")
  private Inquiry inquiry;

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
  public InquiryReply(String content, Integer depth,
    Long parentId, Member member, Inquiry inquiry) {
    this.content = content;
    this.depth = depth;
    this.parentId = parentId;
    this.member = member;
    this.inquiry = inquiry;
  }

  public void delete() {
    this.deleteYn = true;
    this.content = null;
  }

  public void updateReply(String content) {
    if (StringUtils.hasText(content)) this.content = content;
  }

}
