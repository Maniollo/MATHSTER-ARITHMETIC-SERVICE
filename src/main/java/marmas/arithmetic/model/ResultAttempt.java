package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ResultAttempt {
    private final OperationFactors operationFactors;
    private final Long userId;
    private final Integer result;
    private final Boolean isCorrect;
}
