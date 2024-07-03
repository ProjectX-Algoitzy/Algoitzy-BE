package org.example.domain.member.service;


import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.repository.DetailMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailMemberService {

  private final DetailMemberRepository detailMemberRepository;

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return detailMemberRepository.getMemberInfo();
  }
}