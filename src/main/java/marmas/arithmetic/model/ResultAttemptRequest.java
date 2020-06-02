package marmas.arithmetic.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ResultAttemptRequest {
    @NotNull(message = "Operation Factors must be provided")
    private final OperationFactors operationFactors;
    private final int result;
    @AssertFalse(message = "Flag isCorrect cannot be set")
    private final boolean correct;
    private final int attemptNumber;
}
