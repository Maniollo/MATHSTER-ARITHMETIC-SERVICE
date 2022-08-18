package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@AllArgsConstructor
@Getter
public enum MathOperationType {
  ADDITION(
      (Integer::sum),
      ((x1, x2) -> x1 + " + " + x2)
  ),
  SUBTRACTION(
      ((x1, x2) -> x1 - x2),
      ((x1, x2) -> x1 + " - " + x2)
  );
  private final BiFunction<Integer, Integer, Integer> funcCalc;
  private final BiFunction<Integer, Integer, String> funcFormat;
}
