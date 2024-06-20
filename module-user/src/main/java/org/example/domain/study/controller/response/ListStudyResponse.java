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
@Schema(description = "자율 스터디 목록 응답 객체")
public class ListStudyResponse {

  @Default
  List<ListStudyDto> studyList = new ArrayList<>();
}
