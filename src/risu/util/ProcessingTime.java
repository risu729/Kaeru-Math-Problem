package risu.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.tuple.Pair;

public class ProcessingTime {
  
  private static final int CARRY_THRESHOLD = 10;
  
  private final LocalDateTime initial;
  
  public ProcessingTime() {
    initial = LocalDateTime.now();
  }

  // 数値と単位をPairクラスで併せて扱う
  public Pair<Long, TimeUnit> getTime() {
    var dateTime = LocalDateTime.now();
    return getTime(dateTime, suitableUnit(dateTime));
  }

  // 値同士の比較を行うなど、特定の単位に指定する場合
  public Pair<Long, TimeUnit> getTime(final TimeUnit unit) {
    return getTime(LocalDateTime.now(), unit);
  }

  private Pair<Long, TimeUnit> getTime(final LocalDateTime dateTime, final TimeUnit unit) {
    return Pair.of(unit.convert(Duration.between(initial, dateTime)), unit);
  }

  public void printTime() {
    printTime(getTime());
  }

  public void printTime(final TimeUnit unit) {
    printTime(getTime(unit));
  }

  private void printTime(final Pair<Long, TimeUnit> timeWithUnit) {
    final var unitStr = switch (timeWithUnit.getRight()) {
      case NANOSECONDS -> "ns";
      case MICROSECONDS -> "μs";
      case MILLISECONDS -> "ms";
      case SECONDS -> "s";
      case MINUTES -> "min";
      case HOURS -> "h";
      case DAYS -> "d";
    };
    System.out.println("Processing Time: " + timeWithUnit.getLeft() + unitStr);
  }

  private TimeUnit suitableUnit(final LocalDateTime dateTime) {
    // 標準APIの定義順(小->大)に依存 (Java 17時点)
    final TimeUnit[] unitArr = TimeUnit.values();
    // 見やすいよう、数値がCARRY_THRESHOLD以上になる単位に繰り上げ
    for(var unit : unitArr) {
      if (unit == unitArr[unitArr.length - 1]) {
        return unit;
      }
      long t = getTime(dateTime, unit).getLeft();
      if (Math.abs(t) < unit.convert(CARRY_THRESHOLD, unitArr[unit.ordinal() + 1])) {
        return unit;
      }
    }
    return null;
  }
}