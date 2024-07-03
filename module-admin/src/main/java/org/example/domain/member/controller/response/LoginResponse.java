package org.example.domain.member.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "로그인 응답 객체")
public record LoginResponse(
  @Schema(description = "Access Token")
  String accessToken
) {
}
