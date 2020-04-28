package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import marmas.arithmetic.entity.ResultAttemptEntity;
import marmas.arithmetic.exception.InvalidRequestException;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttempt;
import marmas.arithmetic.repository.ResultAttemptRepository;
import org.springframework.stereotype.Service;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
public class ResultAttemptService {
    private final ResultAttemptRepository resultAttemptRepository;

    public ResultAttempt verifyResultAttempt(ResultAttempt resultAttempt) {

        boolean correct = checkAttempt(resultAttempt.getOperationFactors(), resultAttempt.getResult());
        ResultAttemptEntity operation = ResultAttemptEntity.builder()
                .correct(correct)
                .factorA(resultAttempt.getOperationFactors().getFactorA())
                .factorB(resultAttempt.getOperationFactors().getFactorB())
                .result(resultAttempt.getResult())
                .operation(resultAttempt.getOperationFactors().getOperationType())
                .build();
        ResultAttemptEntity saved = resultAttemptRepository.save(operation);

        return new ResultAttempt(
                resultAttempt.getOperationFactors(),
                resultAttempt.getResult(),
                saved.isCorrect()
        );
    }

    private boolean checkAttempt(OperationFactors operationFactors, int result) { MathOperationType operationType = ofNullable(operationFactors.getOperationType())
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
