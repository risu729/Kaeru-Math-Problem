package risu.kaeruproblem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Formula {
  // n = 1 の場合のメモ
  private static Map<Integer, BigDecimal> memo = new HashMap<>();

  public static BigDecimal calc(final int a, final int n) {
    if (n == 1) {
      return hypo1(a);
    } else {
      return BigDecimal.ZERO;
    }
  }

  // in case n = 1
  private static BigDecimal hypo1(final int a) {
    if (memo.containsKey(a)) {
      return memo.get(a);

    } else if (a == 1) {
      var value = BigDecimal.ONE.setScale(5, RoundingMode.HALF_UP);
      memo.put(1, value);
      return value;

    } else {
      var value = BigDecimal.ONE.add(hypo1(a - 1));
      for (int k = 1; k <= a - 1; k++) {
        value = value.add(hypo1(k));
      }
      value = value.divide(new BigDecimal(a), 5, RoundingMode.HALF_UP);
      memo.put(a, value);
      return value;
    }
  }
}