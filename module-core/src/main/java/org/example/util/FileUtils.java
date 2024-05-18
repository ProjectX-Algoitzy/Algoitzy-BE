package org.example.util;

import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.springframework.util.StringUtils;

public class FileUtils {

  /**
   * 확장자 추출
   */
  public static String getFileExtension(String originalName) {
    if (!StringUtils.hasText(originalName) || !originalName.contains(".")) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "확장자를 추출할 수 없습니다.");
    }
    return originalName.substring(originalName.lastIndexOf("."));
  }
}
