package org.example.domain.member.controller.request;

public record ValidateEmailRequest(
  String email,
  String code
) {

}
