package org.example.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.CreateMemberRequest;
import org.example.domain.member.controller.request.ValidateEmailRequest;
import org.example.domain.member.controller.request.ValidateHandleRequest;

@RequiredArgsConstructor
public class MemberService {

  private final CreateMemberService createMemberService;
  public void createMember(CreateMemberRequest request) {
    createMemberService.createMember(request);
  }

  public void validateHandle(ValidateHandleRequest request) {
    createMemberService.validateHandle(request);
  }
  public void validateEmail(ValidateEmailRequest request) {
    createMemberService.validateEmail(request);
  }
}
