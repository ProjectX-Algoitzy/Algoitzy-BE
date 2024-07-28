package org.example.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.util.StringUtils;

public class ArrayUtils {

  /**
   * 배열 중복 원소 검사
   */
  public static <T> boolean isUniqueArray(List<T> array) {
    Set<T> set = new HashSet<>();
    return array.stream().allMatch(set::add);
  }

  /**
   * 숫자로만 이루어진 문자열인지 검사
   */
  public static boolean isAllNumber(String str) {
    if (!StringUtils.hasText(str)) {
      return false;
    }
    return str.matches("\\d+");
  }
}
