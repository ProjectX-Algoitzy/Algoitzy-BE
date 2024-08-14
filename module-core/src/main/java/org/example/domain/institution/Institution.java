package org.example.domain.institution;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.institution.enums.InstitutionType;
import org.example.domain.workbook.Workbook;
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
public class Institution {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Workbook> workbookList = new ArrayList<>();

  @Comment("기관명")
  private String name;

  @Comment("분석 내용")
  private String content;

  @Enumerated(value = EnumType.STRING)
  @Comment("기관 유형")
  private InstitutionType type;

  @Comment("조회수")
  @Column(columnDefinition = "int default 0")
  private Integer viewCount;

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
  public Institution(String name, String content, InstitutionType type) {
    this.name = name;
    this.content = content;
    this.type = type;
  }

  public void update(String name, String content, InstitutionType type) {
    if (StringUtils.hasText(name)) this.name = name;
    if (StringUtils.hasText(content)) this.content = content;
    if (type != null) this.type = type;
  }

  public void syncViewCount(int viewCount) {
    if (this.viewCount == null) this.viewCount = viewCount;
    else this.viewCount += viewCount;
  }
}
