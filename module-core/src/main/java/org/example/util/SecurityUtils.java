package org.example.util;

import java.util.List;
import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.example.domain.member.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  private static Authentication getAuthentication() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "유효하지 않은 토큰 입니다.");
    }
    return authentication;
  }

  public static String getCurrentMemberEmail() {
    Authentication authentication = getAuthentication();
    return authentication.getName();
  }

  public static Role getCurrentMemberRole() {
    Authentication authentication = getAuthentication();

    List<? extends GrantedAuthority> authorityList = authentication.getAuthorities().stream().toList();
    return Role.valueOf(authorityList.get(0).toString());
  }
}
