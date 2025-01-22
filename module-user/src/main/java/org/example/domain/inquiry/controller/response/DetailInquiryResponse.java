package org.example.domain.inquiry.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.inquiry.enums.InquiryCategory;

@Builder(toBuilder = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "문의 상세 조회 응답 객체")
public class DetailInquiryResponse {

  @Schema(description = "카테고리 enum code")
  private String categoryCode;

  @Schema(description = "카테고리명")
  private String categoryName;

  @Schema(description = "문의 제목")
  private String title;

  @Schema(description = "작성자 프로필 이미지")
  private String profileUrl;

  @Schema(description = "작성자")
  private String createdName;

  @Schema(description = "작성자 백준 닉네임")
  private String handle;

  @Schema(description = "작성일")
  private LocalDateTime createdTime;

  @Schema(description = "조회수")
  private int viewCount;

  @Schema(description = "문의 ID")
  private long inquiryId;

  @Schema(description = "문의 내용")
  private String content;

  @Schema(description = "댓글 수")
  private int replyCount;

  @Schema(description = "공개 여부")
  private boolean publicYn;

  @Schema(description = "답변 여부")
  private boolean solvedYn;

  public void updateCategoryName() {
    this.categoryName = InquiryCategory.valueOf(this.categoryCode).getName();
  }
}
