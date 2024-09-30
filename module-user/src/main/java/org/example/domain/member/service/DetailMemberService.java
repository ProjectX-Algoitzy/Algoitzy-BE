package org.example.domain.member.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.Member;
import org.example.domain.member.controller.request.CheckPasswordRequest;
import org.example.domain.member.controller.request.FindEmailRequest;
import org.example.domain.member.controller.response.MemberInfoResponse;
import org.example.domain.member.controller.response.MyPageInfoResponse;
import org.example.domain.member.controller.response.MyPageStudyResponse;
import org.example.domain.member.repository.DetailMemberRepository;
import org.example.domain.member.repository.MemberRepository;
import org.example.domain.study.controller.response.ListStudyDto;
import org.example.domain.study.repository.ListStudyRepository;
import org.example.util.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetailMemberService {

  private final CoreMemberService coreMemberService;
  private final DetailMemberRepository detailMemberRepository;
  private final ListStudyRepository listStudyRepository;
  private final MemberRepository memberRepository;

  private final PasswordEncoder encoder;

  /**
   * 로그인 멤버 정보
   */
  public MemberInfoResponse getLoginMemberInfo() {
    return detailMemberRepository.getLoginMemberInfo();
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

  /**
   * 마이페이지 멤버 정보
   */
  public MyPageInfoResponse getMyPageInfo(Long memberId) {
    return detailMemberRepository.getMyPageInfo(memberId);
  }

  public MyPageStudyResponse getMyPageStudy(Long memberId) {
    List<ListStudyDto> passStudyList = listStudyRepository.getMyPageStudy(memberId, true);
    passStudyList.forEach(dto -> dto.updateType(dto.getStudyType()));

    List<ListStudyDto> applyStudyList = new ArrayList<>();
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    // 타인의 마이페이지 열람 시 지원한 스터디 미노출
    if (member.getId().equals(memberId)) {
      applyStudyList = new ArrayList<>(listStudyRepository.getMyPageStudy(memberId, false));
      applyStudyList.forEach(dto -> dto.updateType(dto.getStudyType()));
    }

    return MyPageStudyResponse.builder()
      .passStudyList(passStudyList)
      .applyStudyList(applyStudyList)
      .build();
  }

  /**
   * 내 정보 조회
   */
  public MemberInfoResponse getMyInfo() {
    return detailMemberRepository.getMyInfo();
  }

  /**
   * 비밀번호 확인
   */
  public Boolean checkPassword(CheckPasswordRequest request) {
    Member member = coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail());
    return encoder.matches(request.password(), member.getPassword());
  }
}