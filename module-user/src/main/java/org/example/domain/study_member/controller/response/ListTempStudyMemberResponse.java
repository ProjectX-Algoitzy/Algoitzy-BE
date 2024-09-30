package org.example.domain.study_member.controller.response;

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
@Schema(description = "자율 스터디원 목록 응답 객체")
public class ListTempStudyMemberResponse {

  @Default
  @Schema(description = "자율 스터디원 목록")
  private List<ListTempStudyMemberDto> studyMemberList = new ArrayList<>();

}
