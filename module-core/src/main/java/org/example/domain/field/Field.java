package org.example.domain.field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.select_question.SelectQuestion;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Field {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "select_question_id")
  private SelectQuestion selectQuestion;
  private LocalDate dateField;
  private String stringField;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;
  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public Field(SelectQuestion selectQuestion, LocalDate dateField, String stringField) {
    this.selectQuestion = selectQuestion;
    this.dateField = dateField;
    this.stringField = stringField;
  }
}
