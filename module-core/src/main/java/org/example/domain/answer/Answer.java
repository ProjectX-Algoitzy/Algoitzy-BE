package org.example.domain.answer;

import jakarta.persistence.Column;
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
import org.example.domain.field.Field;
import org.example.domain.member.Member;
import org.example.domain.select_question.SelectQuestion;
import org.example.domain.text_question.TextQuestion;
import org.springframework.data.annotation.CreatedDate;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "text_question_id")
  private TextQuestion textQuestion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "select_question_id")
  private SelectQuestion selectQuestion;

  @ManyToOne(fetch = FetchType.LAZY)
  private Field field;

  private String textAnswer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;
  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public Answer(TextQuestion textQuestion, SelectQuestion selectQuestion, Field field, String textAnswer, Member member) {
    this.textQuestion = textQuestion;
    this.selectQuestion = selectQuestion;
    this.field = field;
    this.textAnswer = textAnswer;
    this.member = member;
  }
}
