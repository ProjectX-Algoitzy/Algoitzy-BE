package org.example.domain.inquiry;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.inquiry.enums.InquiryCategory;
import org.example.domain.inquiry_reply.InquiryReply;
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
public class Inquiry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Comment("문의 카테고리")
  private InquiryCategory category;

  @Comment("문의 제목")
  private String title;

  @Comment("문의 내용")
  @Column(length = 1000000)
  private String content;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("답변 여부")
  private Boolean solvedYn;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("공개 여부")
  private Boolean publicYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "inquiry", orphanRemoval = true)
  private List<InquiryReply> replyList = new ArrayList<>();

  @CreatedDate
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @CreatedBy
  @Column(updatable = false)
  private String createdBy;

  @LastModifiedBy
  private String updatedBy;

  @Builder
  public Inquiry(InquiryCategory category, String title, String content,
    Boolean publicYn, Member member) {
    this.category = category;
    this.title = title;
    this.content = content;
    this.publicYn = publicYn;
    this.member = member;
  }

  public void updateBoard(InquiryCategory category, boolean publicYn, String title, String content) {
    this.category = category;
    this.publicYn = publicYn;
    if (StringUtils.hasText(title)) this.title = title;
    if (StringUtils.hasText(content)) this.content = content;
  }

  public void updatePublicYn() {
    this.publicYn = !this.publicYn;
  }

}
