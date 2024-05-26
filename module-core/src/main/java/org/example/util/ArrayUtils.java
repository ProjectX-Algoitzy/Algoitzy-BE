package org.example.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayUtils {

  /**
   *
   */
  public static <T> boolean isUniqueArray(List<T> array) {
    Set<T> set = new HashSet<>();
    return array.stream().allMatch(set::add);
  }
}
