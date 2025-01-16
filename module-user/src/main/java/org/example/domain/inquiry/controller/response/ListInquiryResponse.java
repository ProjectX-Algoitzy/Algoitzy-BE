package org.example.domain.inquiry.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "문의 목록 조회 응답 객체")
public class ListInquiryResponse {

    @Default
    @Schema(description = "문의 목록")
    private List<ListInquiryDto> inquiryList = new ArrayList<>();

    @Schema(description = "총 문의 수")
    private long totalCount;

}
