package risu729.kaeruproblem.data;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Settings {
  private int[] a;
  private int[] n;
  private int times;

  public void checkFormat() {
    if (a.length == 0 || a.length > 2 || n.length == 0 || n.length > 2) {
      var msg = "Some arrays in Settings.json have 0 or more than 2 elements.";
      throw new IllegalStateException(msg);
    }

    IntStream.concat(IntStream.concat(Arrays.stream(a), Arrays.stream(n)), IntStream.of(times))
        .forEach(this::checkPositive);
  }

  public int getAMin() {
    return getMin(a);
  }

  public int getAMax() {
    return getMax(a);
  }

  public int getNMin() {
    return getMin(n);
  }

  public int getNMax() {
    return getMax(n);
  }

  public int getTimes() {
    return times;
  }

  private void checkPositive(final int t) {
    if (t <= 0) {
      var msg = "Some arguments in Settings.json are non-positive integers.";
      throw new IllegalStateException(msg);
    }
  }

  private int getMin(final int[] arr) {
    return arr.length == 1 ? arr[0] : Math.min(arr[0], arr[1]);
  }

  private int getMax(final int[] arr) {
    return arr.length == 1 ? arr[0] : Math.max(arr[0], arr[1]);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    return (obj instanceof Settings other)
        && Arrays.equals(a, other.a)
        && Arrays.equals(n, other.n)
        && times == other.times;
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[] { a, n, times });
  }

  @Override
  public String toString() {
    return getClass().getSimpleName()
        + "[a=" + Arrays.toString(a)
        + ", n=" + Arrays.toString(n)
        + ", times=" + times + "]";
  }
}