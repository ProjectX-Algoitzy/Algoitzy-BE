package org.example.domain.workbook.enums;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CodingTestBasicMockTest {

  WEEK1(1, List.of(2557, 8393, 2741, 14579, 2742, 10869, 2739, 10950, 7287, 14681, 3053, 1330, 1000, 2752, 15917, 5361, 20540, 9498, 2420, 2839)),
  WEEK2(2, List.of(10824, 2438, 2576, 2908, 10808, 10871, 1267, 11365, 11721, 2675, 1264, 11931, 2750, 1764, 10867, 17219, 2562, 17263, 8958, 1152)),
  WEEK3(3, List.of(3181, 6996, 1302, 11654, 5218, 15813, 3059, 1157, 11718, 10823, 11719, 1371, 1673, 6502, 1362, 16205, 14935, 2495, 14910, 2869)),
  WEEK4(4, List.of(11005, 12174, 2745, 14915, 11179, 13235, 11068, 3062, 17502, 14561, 10828, 3986, 1874, 10773, 4949, 2596, 1316, 2789, 14582, 7510)),
  WEEK5(5, List.of(1100, 10864, 2999, 12759, 1181, 10825, 2566, 5533, 10798, 1018, 14592, 14593, 8979, 15905, 2504, 7523, 7795, 2729, 18406, 2828)),
  WEEK6(6, List.of(5598, 15874, 11655, 11575, 1718, 10974, 2798, 15649, 15650, 1182, 15652, 10845, 1966, 10866, 10102, 2309, 11880, 12789, 2902, 4447)),
  WEEK7(7, List.of(1942, 2530, 2525, 3029, 1408, 2852, 10872, 15655, 15651, 15654, 15656, 15657, 17249, 2774, 11383, 15351, 16955, 16435, 17608, 10865)),
  WEEK8(8, List.of(14650, 9663, 1012, 1743, 1926, 3184, 1002, 4963, 14889, 10819, 10974, 12101, 25602, 15658));

  public final int week;
  public final List<Integer> problemNumberList;

  public static CodingTestBasicMockTest findByWeek(int week) {
    return Arrays.stream(values())
      .filter(value -> value.week == week)
      .findFirst()
      .orElseThrow();
  }
}
