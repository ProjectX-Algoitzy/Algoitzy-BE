package org.example.domain.answer;

import jakarta.persistence.CascadeType;
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
import org.example.domain.application.Application;
import org.example.domain.select_answer.SelectAnswer;
import org.example.domain.text_answer.TextAnswer;
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
public class Answer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Setter
  @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TextAnswer> textAnswerList = new ArrayList<>();

  @Setter
  @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SelectAnswer> selectAnswerList = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "application_id")
  private Application application;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("확정 여부")
  private Boolean confirmYN;

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
  public Answer(Application application, Boolean confirmYN) {
    this.application = application;
    this.confirmYN = confirmYN;
  }
}
