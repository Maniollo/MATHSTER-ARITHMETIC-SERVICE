package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class OperationFactors {
    private final int factorA;
    private final int factorB;
    private final MathOperationType operationType;
}
