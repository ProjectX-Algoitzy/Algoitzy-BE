package org.example.domain.member.controller.request;

public record CreateMemberRequest(
  String email,
  String password,
  String checkPassword,
  String name,
  String handle,
  String phoneNumber

) {

}
