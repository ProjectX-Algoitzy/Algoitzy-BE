package org.example.domain.curriculum.controller.response;

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
@Schema(description = "커리큘럼 목록 조회 응답 객체")
public class ListCurriculumResponse {

  @Default
  @Schema(description = "커리큘럼 목록")
  private List<ListCurriculumDto> curriculumList = new ArrayList<>();
}
