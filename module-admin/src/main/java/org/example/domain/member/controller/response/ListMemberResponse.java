package org.example.domain.member.controller.response;

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
@Schema(description = "유저 목록 조회 응답 객체")
public class ListMemberResponse {

  @Default
  @Schema(description = "유저 목록")
  private List<ListMemberDto> memberList = new ArrayList<>();

  @Schema(description = "총 유저 수")
  private long totalCount;
}
