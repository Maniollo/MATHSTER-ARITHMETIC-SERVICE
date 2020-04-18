package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OperationFactors {
    private final int factorA;
    private final int factorB;
    private final MathOperationType operationType;
}
