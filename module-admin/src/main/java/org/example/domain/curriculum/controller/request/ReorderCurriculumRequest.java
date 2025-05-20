package org.example.domain.curriculum.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "커리큘럼 순서 변경 요청 객체")
public record ReorderCurriculumRequest(

  @Schema(description = "커리큘럼 ID 목록")
  List<Long> curriculumIdList
) {
}
