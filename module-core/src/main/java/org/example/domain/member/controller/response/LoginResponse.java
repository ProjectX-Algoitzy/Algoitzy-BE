package org.example.domain.member.controller.response;

import lombok.Builder;

@Builder
public record LoginResponse(
  String accessToken
) {
}
