package org.example.util;

import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

  public static String getCurrentMemberEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || authentication.getName() == null) {
      throw new GeneralException(ErrorStatus.VALIDATION_ERROR, "유효하지 않은 토큰 입니다.");
    }
    return authentication.getName();
  }
}
