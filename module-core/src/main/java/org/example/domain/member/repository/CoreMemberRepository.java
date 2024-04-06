package org.example.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CoreMemberRepository {

  private final MemberRepository memberRepository;

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email)
      .orElseThrow(() -> new GeneralException(ErrorStatus.NOT_FOUND, "존재하지 않는 Email 입니다."));
  }

}
