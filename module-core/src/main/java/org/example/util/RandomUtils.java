package org.example.util;

import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomUtils {

  /**
   * 4자리 난수 생성
   */
  public static String getRandomNumber() {
    StringBuilder result = new StringBuilder();
    SecureRandom random = new SecureRandom();
    while (result.length() < 4) {
      int num = random.nextInt(10);
      result.append(num);
    }
    return result.toString();
  }
}
