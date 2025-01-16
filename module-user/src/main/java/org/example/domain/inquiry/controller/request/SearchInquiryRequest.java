package org.example.domain.inquiry.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import org.example.domain.inquiry.enums.InquiryCategory;
import org.example.domain.inquiry.enums.InquirySort;
import org.springframework.data.domain.PageRequest;

@Schema(description = "문의 목록 검색 요청 객체")
public record SearchInquiryRequest(

    @Schema(description = "검색 키워드(문의 제목, 작성자)")
    String searchKeyword,

    @Schema(description = "문의 카테고리")
    InquiryCategory category,

    @Schema(description = "문의 정렬 조건", allowableValues = {"LATEST", "VIEW_COUNT"})
    InquirySort sort,

    @Min(1)
    @Schema(description = "페이지 번호", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int page,

    @Min(10)
    @Schema(description = "페이지별 개수", type = "integer", requiredMode = RequiredMode.REQUIRED)
    int size
) {

    public PageRequest pageRequest() {
        return PageRequest.of(page - 1, size);
    }

}