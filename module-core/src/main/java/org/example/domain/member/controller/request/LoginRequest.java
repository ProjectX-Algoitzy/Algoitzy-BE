package org.example.domain.member.controller.request;

public record LoginRequest(
  String email,
  String password
) {

}
