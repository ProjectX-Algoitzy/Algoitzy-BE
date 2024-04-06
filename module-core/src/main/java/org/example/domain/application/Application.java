package org.example.domain.application;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.study.Study;
import org.example.domain.text_question.TextQuestion;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_id")
  private Study study;

  @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TextQuestion> textQuestionList = new ArrayList<>();

  @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SelectQuestion> selectQuestionList = new ArrayList<>();

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;
  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public Application(Study study, List<TextQuestion> textQuestionList, List<SelectQuestion> selectQuestionList) {
    this.study = study;
    this.textQuestionList = textQuestionList;
    this.selectQuestionList = selectQuestionList;
  }
}
