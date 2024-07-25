package org.example.domain.controller.response;

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
@Schema(description = "문제집 목록 응답 객체")
public class ListWorkbookResponse {

  @Default
  @Schema(description = "문제집 목록")
  private List<ListWorkbookDto> workbookList = new ArrayList<>();
}
