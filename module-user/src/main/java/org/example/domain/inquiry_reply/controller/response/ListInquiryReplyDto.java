package org.example.domain.inquiry_reply.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.member.enums.Role;
import org.example.util.ValueUtils;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "문의 댓글 목록 조회 응답 객체")
public class ListInquiryReplyDto {

  @Schema(description = "댓글 ID")
  private long replyId;

  @Schema(description = "부모 댓글 ID")
  private Long parentReplyId;

  @Schema(description = "내용")
  private String content;

  @Schema(description = "작성자 백준 닉네임")
  private String handle;

  @JsonIgnore
  @Schema(description = "작성자 권한")
  private Role createdRole;

  @Schema(description = "작성자")
  private String createdName;

  @Schema(description = "작성일")
  private LocalDateTime createdTime;

  @Schema(description = "내 댓글 여부")
  private boolean myReplyYn;

  @Schema(description = "문의 작성자 여부")
  private boolean myInquiryYn;

  @Schema(description = "작성자 프로필 이미지 URL")
  private String profileUrl;

  @Schema(description = "삭제 여부")
  private boolean deleteYn;

  @Default
  @Schema(description = "자식 댓글 목록")
  private List<ListInquiryReplyDto> childrenReplyList = new ArrayList<>();

  public void setAdminInfo() {
    this.createdName = "관리자";
    this.handle = null;
    this.profileUrl = ValueUtils.ADMIN_PROFILE_IMAGE;
  }
}
