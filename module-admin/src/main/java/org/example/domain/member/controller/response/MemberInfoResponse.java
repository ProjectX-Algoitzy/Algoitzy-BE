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
@Schema(description = "로그인 멤버 정보 응답 객체")
public class MemberInfoResponse {

  @Schema(description = "프로필 이미지")
  private String profileUrl;

  @Schema(description = "이름")
  private String name;

  @Schema(description = "백준 닉네임")
  private String handle;

  @Schema(description = "역할")
  private Role role;
}
