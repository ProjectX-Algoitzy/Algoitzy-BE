package org.example.domain.study.controller.response;

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
@Schema(description = "정규 스터디 목록 응답 객체")
public class ListRegularStudyResponse {

  @Default
  @Schema(description = "정규 스터디 목록")
  private List<ListRegularStudyDto> studyList = new ArrayList<>();
}
