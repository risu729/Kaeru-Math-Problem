package risu.kaeruproblem.data;

import java.math.BigDecimal;

public record Result(
  int a,
  int n,
  BigDecimal experiment,
  BigDecimal formula) {
}