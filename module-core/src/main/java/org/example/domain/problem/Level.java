package org.example.domain.problem;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum Level {

  BRONZE5(1, "https://static.solved.ac/tier_small/1.svg"),
  BRONZE4(2, "https://static.solved.ac/tier_small/2.svg"),
  BRONZE3(3, "https://static.solved.ac/tier_small/3.svg"),
  BRONZE2(4, "https://static.solved.ac/tier_small/4.svg"),
  BRONZE1(5, "https://static.solved.ac/tier_small/5.svg"),

  SILVER5(6, "https://static.solved.ac/tier_small/6.svg"),
  SILVER4(7, "https://static.solved.ac/tier_small/7.svg"),
  SILVER3(8, "https://static.solved.ac/tier_small/8.svg"),
  SILVER2(9, "https://static.solved.ac/tier_small/9.svg"),
  SILVER1(10, "https://static.solved.ac/tier_small/10.svg"),

  GOLD5(11, "https://static.solved.ac/tier_small/11.svg"),
  GOLD4(12, "https://static.solved.ac/tier_small/12.svg"),
  GOLD3(13, "https://static.solved.ac/tier_small/13.svg"),
  GOLD2(14, "https://static.solved.ac/tier_small/14.svg"),
  GOLD1(15, "https://static.solved.ac/tier_small/15.svg"),

  PLATINUM5(16, "https://static.solved.ac/tier_small/16.svg"),
  PLATINUM4(17, "https://static.solved.ac/tier_small/17.svg"),
  PLATINUM3(18, "https://static.solved.ac/tier_small/18.svg"),
  PLATINUM2(19, "https://static.solved.ac/tier_small/19.svg"),
  PLATINUM1(20, "https://static.solved.ac/tier_small/20.svg"),

  DIAMOND5(21, "https://static.solved.ac/tier_small/21.svg"),
  DIAMOND4(22, "https://static.solved.ac/tier_small/22.svg"),
  DIAMOND3(23, "https://static.solved.ac/tier_small/23.svg"),
  DIAMOND2(24, "https://static.solved.ac/tier_small/24.svg"),
  DIAMOND1(25, "https://static.solved.ac/tier_small/25.svg"),

  RUBY5(26, "https://static.solved.ac/tier_small/26.svg"),
  RUBY4(27, "https://static.solved.ac/tier_small/27.svg"),
  RUBY3(28, "https://static.solved.ac/tier_small/28.svg"),
  RUBY2(29, "https://static.solved.ac/tier_small/29.svg"),
  RUBY1(30, "https://static.solved.ac/tier_small/30.svg");

  private final int level;
  private final String imageUrl;

  public static Level findByLevel(int level) {
    return Arrays.stream(values())
      .filter(value -> value.level == level)
      .findFirst()
      .orElseThrow();
  }
}
