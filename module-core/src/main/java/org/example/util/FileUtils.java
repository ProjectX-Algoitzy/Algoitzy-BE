package org.example.util;

import org.example.api_response.exception.GeneralException;
import org.example.api_response.status.ErrorStatus;
import org.springframework.util.StringUtils;

public class FileUtils {

  private static final String BASIC_IMAGE = "/basic-image/";
  private static final String EMAIL = "/email/";

  /**
   * 확장자 추출
   */
  public static String getFileExtension(String originalName) {
    if (!StringUtils.hasText(originalName) || !originalName.contains(".")) {
      throw new GeneralException(ErrorStatus.BAD_REQUEST, "확장자를 추출할 수 없습니다.");
    }
    return originalName.substring(originalName.lastIndexOf(".") + 1);
  }

  /**
   * 기본 이미지 여부
   */
  public static boolean isBasicImage(String fileUrl) {
    return fileUrl.contains(BASIC_IMAGE) || fileUrl.contains(EMAIL);
  }

  /**
   * 파일 용량 변환
   */
  public static String getFileSize(double fileSize) {
    if (fileSize < 100) return Math.round(fileSize * 100) / 100.0 + "B";
    else if (fileSize < 1000000) return Math.round(fileSize / 1000 * 100) / 100.0 + "KB";
    else return Math.round(fileSize / 1000000 * 100) / 100.0 + "MB";
  }

}
