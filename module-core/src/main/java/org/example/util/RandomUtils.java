package org.example.util;

import java.security.SecureRandom;
import java.util.UUID;

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

  /**
   * 16자리 문자열 생성
   */
  public static String getRandomString(int length) {
    return UUID.randomUUID().toString().substring(0, length).replace("-","X");
  }
}
