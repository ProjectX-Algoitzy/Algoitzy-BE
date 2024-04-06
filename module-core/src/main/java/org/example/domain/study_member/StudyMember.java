package org.example.domain.study_member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.attendance.Attendance;
import org.example.domain.attendance_request.AttendanceRequest;
import org.example.domain.interview.Interview;
import org.example.domain.member.Member;
import org.example.domain.study.Study;
import org.example.domain.study_member.enums.StudyMemberRole;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class StudyMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "study_id")
  private Study study;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @OneToOne(mappedBy = "studyMember", cascade = CascadeType.ALL, orphanRemoval = true)
  private Interview interview;

  @OneToMany(mappedBy = "studyMember", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AttendanceRequest> attendanceRequestList = new ArrayList<>();

  @OneToMany(mappedBy = "studyMember", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Attendance> attendanceList = new ArrayList<>();

  @Enumerated(value = EnumType.STRING)
  private StudyMemberRole role;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdTime;
  @LastModifiedDate
  private LocalDateTime updatedTime;

  @Builder
  public StudyMember(Study study, Member member, Interview interview, List<AttendanceRequest> attendanceRequestList, List<Attendance> attendanceList,
    StudyMemberRole role) {
    this.study = study;
    this.member = member;
    this.interview = interview;
    this.attendanceRequestList = attendanceRequestList;
    this.attendanceList = attendanceList;
    this.role = role;
  }
}
