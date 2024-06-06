package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;

@Schema(description = "유저 목록 검색 요청 객체")
public record SearchMemberRequest(

  @Schema(description = "검색 키워드(이름, 백준 닉네임)")
  String searchKeyword,

  @Min(1)
  @Schema(description = "페이지 번호", type = "integer", requiredMode = RequiredMode.REQUIRED)
  int page,

  @Min(5)
  @Schema(description = "페이지별 개수", type = "integer", requiredMode = RequiredMode.REQUIRED)
  int size
) {

  public PageRequest pageRequest() {
    return PageRequest.of(page - 1, size);
  }

}
