package org.example.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.config.jpa.BooleanToYNConverter;
import org.example.config.jpa.CryptoConverter;
import org.example.domain.member.enums.Role;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("프로필 이미지")
  private String profileUrl;

  @Column(unique = true, nullable = false)
  @Comment("이메일")
  private String email;

  @Column(unique = true, nullable = false)
  @Comment("비밀번호")
  private String password;

  @Column(nullable = false)
  @Comment("이름")
  private String name;

  @Column(nullable = false)
  @Comment("학년")
  private Integer grade;

  @Column(nullable = false)
  @Comment("학과")
  private String major;

  @Column(unique = true, nullable = false)
  @Comment("백준 닉네임")
  private String handle;

  @Convert(converter = CryptoConverter.class)
  @Column(unique = true, nullable = false)
  @Comment("핸드폰 번호")
  private String phoneNumber;

  @Enumerated(value = EnumType.STRING)
  @Comment("역할")
  private Role role;

  @Convert(converter = BooleanToYNConverter.class)
  @Column(nullable = false, columnDefinition = "char(1) default 'N'")
  @Comment("기수 참여 제한 여부")
  private Boolean blockYN;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;

  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public Member(String profileUrl, String email, String password, String name, String handle, String phoneNumber, Role role,
    Integer grade, String major) {
    this.profileUrl = profileUrl;
    this.email = email;
    this.password = password;
    this.name = name;
    this.handle = handle;
    this.phoneNumber = phoneNumber;
    this.role = role;
    this.grade = grade;
    this.major = major;
  }

  public void updatePassword(String password) {
    this.password = password;
  }

  public void updateRole(Role role) {
    this.role = role;
  }

  public void updateBlockYN(Boolean blockYN) {
    this.blockYN = blockYN;
  }
}
