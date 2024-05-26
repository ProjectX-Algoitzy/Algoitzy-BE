package org.example.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayUtils {

  /**
   * 배열 중복 원소 검사
   */
  public static <T> boolean isUniqueArray(List<T> array) {
    Set<T> set = new HashSet<>();
    return array.stream().allMatch(set::add);
  }
}
