package org.example.domain.member.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Access Token 재발급 요청 객체")
public record RefreshAccessTokenRequest(

  @NotBlank
  @Schema(description = "Access Token")
  String accessToken
) {

}
