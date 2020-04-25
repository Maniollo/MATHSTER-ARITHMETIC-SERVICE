package marmas.arithmetic.service;

import marmas.arithmetic.exception.InvalidRequestException;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttempt;
import org.springframework.stereotype.Service;

@Service
public class ResultAttemptService {
    public ResultAttempt verifyResultAttempt(ResultAttempt resultAttempt) {

        if (resultAttempt.getIsCorrect()) {
            throw new InvalidRequestException();
        }

        return new ResultAttempt(
                resultAttempt.getOperationFactors(),
                resultAttempt.getUserId(),
                resultAttempt.getResult(),
                checkAttempt(resultAttempt.getOperationFactors(), resultAttempt.getResult())
        );
    }

    private boolean checkAttempt(OperationFactors operationFactors, Integer result) {
        switch (operationFactors.getOperationType()) {
            case ADDITION:
                return operationFactors.getFactorA() + operationFactors.getFactorB() == result;
            case SUBTRACTION:
                return operationFactors.getFactorA() - operationFactors.getFactorB() == result;
            default:
                // TODO: Add exception handler and exception msg
                throw new InvalidRequestException();
        }
    }
}
