package risu.kaeruproblem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.math3.random.MersenneTwister;

public class Experiment {
  private static final MersenneTwister rnd = new MersenneTwister();

  public static BigDecimal expt(final int a, final int n, final int times) {
    int sum = 0;
    // ラムダ式内で使用する変数は final or effectively final である必要がある
    final int aForLambda = a;
    List<Integer> seq = IntStream.rangeClosed(1, a * n)
        .map(k -> k % aForLambda + 1)
        .boxed()
        // Collections.swap でランダムアクセスを繰り返すため ArrayList
        .collect(Collectors.toCollection(ArrayList::new));

    for (int i = 0; i < times; i++) {
      // Collections.shuffle の改変
      List<Integer> randomSeq = new ArrayList<Integer>(seq);
      for (int j = randomSeq.size(); j > 1; j--) {
        Collections.swap(randomSeq, j - 1, rnd.nextInt(j));
      }
      // 要素の削除を繰り返すので LinkedList
      randomSeq = new LinkedList<Integer>(randomSeq);

      for (int j = 1; j <= a + 1; j++) {
        // indexOf は最初の要素から順に検索するので LinkedList で問題ない
        var index = randomSeq.indexOf(j);
        if (index == -1) {
          sum += j - 1;
          break;
        } else {
          randomSeq.subList(0, index + 1).clear();
        }
      }
    }
    return new BigDecimal(sum)
        .divide(new BigDecimal(times), 5, RoundingMode.HALF_UP);
  }
}