package marmas.arithmetic.service;

import marmas.arithmetic.exception.InvalidRequestException;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttempt;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
public class ResultAttemptService {
    public ResultAttempt verifyResultAttempt(ResultAttempt resultAttempt) {
        return new ResultAttempt(
                resultAttempt.getOperationFactors(),
                resultAttempt.getResult(),
                checkAttempt(resultAttempt.getOperationFactors(), resultAttempt.getResult())
        );
    }

    private boolean checkAttempt(OperationFactors operationFactors, int result) {
        MathOperationType operationType = ofNullable(operationFactors.getOperationType())
                .orElseThrow(() -> new InvalidRequestException("Operation type is missing."));
        switch (operationType) {
            case ADDITION:
                return operationFactors.getFactorA() + operationFactors.getFactorB() == result;
            case SUBTRACTION:
                return operationFactors.getFactorA() - operationFactors.getFactorB() == result;
            default:
                throw new InvalidRequestException("Unsupported operation type.");
        }
    }
}
