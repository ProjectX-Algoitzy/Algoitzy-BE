package org.example.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.domain.member.enums.Role;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "유저 목록 조회 응답 dto")
public class ListMemberDto {

  @Schema(description = "유저 ID")
  private long memberId;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "백준 닉네임")
  private String handle;

  @Schema(description = "학과")
  private String major;

  @Schema(description = "역할")
  private Role role;
}
