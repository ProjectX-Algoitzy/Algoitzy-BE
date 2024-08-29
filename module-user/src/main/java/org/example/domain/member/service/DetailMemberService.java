package org.example.domain.member.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.FindEmailRequest;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.repository.DetailMemberRepository;
import org.example.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailMemberService {

  private final DetailMemberRepository detailMemberRepository;
  private final MemberRepository memberRepository;

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getMemberInfo() {
    return detailMemberRepository.getMemberInfo();
  }

  /**
   * 아이디(이메일 찾기)
   */
  public String findEmail(FindEmailRequest request) {
    Optional<Member> optionalMember = memberRepository.findByNameAndPhoneNumber(request.name(), request.phoneNumber());
    if (optionalMember.isEmpty()) {
      throw new GeneralException(ErrorStatus.NOTICE_NOT_FOUND, "일치하는 회원 정보가 없습니다.");
    }

    return optionalMember.get().getEmail();
  }
}