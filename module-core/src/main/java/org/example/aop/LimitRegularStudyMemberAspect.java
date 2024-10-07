package org.example.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.enums.Role;
import org.example.domain.member.repository.CoreDetailStudyMemberRepository;
import org.example.domain.member.service.CoreMemberService;
import org.example.util.SecurityUtils;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LimitRegularStudyMemberAspect {

  private final CoreMemberService coreMemberService;
  private final CoreDetailStudyMemberRepository coreDetailStudyMemberRepository;

  @Pointcut("@annotation(org.example.aop.LimitRegularStudyMember)")
  public void pointcut() {
  }

  @Before("pointcut()")
  public void limitRegularStudyMember() {
    if (!coreDetailStudyMemberRepository.isRegularStudyMember()
      && coreMemberService.findByEmail(SecurityUtils.getCurrentMemberEmail()).getRole().equals(Role.ROLE_USER)) {
      throw new GeneralException(ErrorStatus.NOTICE_UNAUTHORIZED, "정규 스터디원만 접근 가능합니다.");
    }
  }

}
