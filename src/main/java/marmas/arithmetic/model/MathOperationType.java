package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MathOperationType {
    ADDITION("+"),
    SUBTRACTION("-");
    private final String sign;
}
