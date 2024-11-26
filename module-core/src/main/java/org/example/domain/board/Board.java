package org.example.domain.board;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.Lob;
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
import lombok.Setter;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.domain.board.enums.BoardCategory;
import org.example.domain.board_file.BoardFile;
import org.example.domain.board_like.BoardLike;
import org.example.domain.member.Member;
import org.example.domain.reply.Reply;
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
public class Board {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Comment("게시물 카테고리")
  private BoardCategory category;

  @Comment("게시물 제목")
  private String title;

  @Comment("게시물 내용")
  @Lob
  private String content;

  @Comment("게시물 조회수")
  @Column(columnDefinition = "integer default 0")
  private Integer viewCount = 0;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("최종 저장 여부")
  private Boolean saveYn;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("고정 여부(공지사항 한정 필드)")
  private Boolean fixYn;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("삭제 여부")
  private Boolean deleteYn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToMany(mappedBy = "board", orphanRemoval = true)
  private List<Reply> replyList = new ArrayList<>();

  @Setter
  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoardFile> boardFileList = new ArrayList<>();

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BoardLike> boardLikeList = new ArrayList<>();

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
  public Board(BoardCategory category, String title, String content,
    Boolean saveYn, Member member) {
    this.category = category;
    this.title = title;
    this.content = content;
    this.saveYn = saveYn;
    this.member = member;
  }

  public void updateBoard(String title, String content, boolean saveYn) {
    if (StringUtils.hasText(title)) this.title = title;
    if (StringUtils.hasText(content)) this.content = content;
    this.saveYn = saveYn;
  }

  public void updateFixYn() {
    this.fixYn = !this.fixYn;
  }

  public void delete() {
    this.deleteYn = true;
    this.content = null;
  }
}
