package org.example.domain.member.controller.request;

import jakarta.validation.constraints.NotBlank;

public record ValidateEmailRequest(
  String email,
  @NotBlank
  String code
) {

}
