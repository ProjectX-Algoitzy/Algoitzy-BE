package org.example.domain.member.controller.request;

public record ValidatePhoneNumberRequest(
  String phoneNumber,
  String code
) {

}
