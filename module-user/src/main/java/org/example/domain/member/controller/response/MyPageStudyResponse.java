package org.example.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.study.controller.response.ListStudyDto;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "마이페이지 스터디 응답 객체")
public class MyPageStudyResponse {

  @Default
  @Schema(description = "참여 중인 스터디 목록")
  private List<ListStudyDto> passStudyList = new ArrayList<>();

  @Default
  @Schema(description = "지원한 스터디 목록")
  private List<ListStudyDto> applyStudyList = new ArrayList<>();
}
