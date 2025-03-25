package org.example.domain.inquiry.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.example.domain.inquiry.enums.InquiryCategory;

@Schema(description = "문의 생성 요청 객체")
public record CreateInquiryRequest(

  @Schema(description = "문의 카테고리")
  InquiryCategory category,

  @Schema(description = "문의 제목")
  String title,

  @Schema(description = "문의 내용")
  String content,

  @NotNull
  @Schema(description = "문의 공개 여부")
  Boolean publicYn

) {

}
