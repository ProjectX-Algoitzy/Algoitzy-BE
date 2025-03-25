package org.example.domain.inquiry.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.inquiry.enums.InquiryCategory;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "문의 목록 조회 응답 DTO")
public class ListInquiryDto {

  @Schema(description = "문의 ID")
  private long inquiryId;

  @Schema(description = "카테고리 코드")
  private String categoryCode;

  @Schema(description = "카테고리명")
  private String categoryName;

  @Schema(description = "제목")
  private String title;

  @Schema(description = "작성자")
  private String createdName;

  @Schema(description = "작성일")
  private LocalDateTime createdTime;

  @Schema(description = "조회수")
  private int viewCount;

  @Schema(description = "공개 여부")
  private boolean publicYn;

  @Schema(description = "답변 여부")
  private boolean solvedYn;

  public void updateCategoryName() {
    this.categoryName = InquiryCategory.valueOf(this.categoryCode).getName();
  }
}