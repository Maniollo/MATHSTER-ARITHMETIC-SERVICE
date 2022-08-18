package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import marmas.arithmetic.entity.ResultAttemptEntity;
import marmas.arithmetic.exception.InvalidRequestException;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttemptRequest;
import marmas.arithmetic.model.ResultAttemptResponse;
import marmas.arithmetic.repository.ResultAttemptRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static java.util.Optional.ofNullable;

@Service
@AllArgsConstructor
public class ResultAttemptService {
    private final ResultAttemptRepository resultAttemptRepository;

    public ResultAttemptResponse verifyResultAttempt(ResultAttemptRequest resultAttemptRequest) {
        @NotNull(message = "Operation Factors must be provided")
        OperationFactors operationFactors = resultAttemptRequest.getOperationFactors();
        boolean correct = checkAttempt(operationFactors, resultAttemptRequest.getResult());
        ResultAttemptEntity operation = ResultAttemptEntity.builder()
                .correct(correct)
                .factorA(operationFactors.getFactorA())
                .factorB(operationFactors.getFactorB())
                .result(resultAttemptRequest.getResult())
                .operation(operationFactors.getOperationType())
                .build();
        ResultAttemptEntity saved = resultAttemptRepository.save(operation);

        return new ResultAttemptResponse(saved.isCorrect());
    }

    private boolean checkAttempt(OperationFactors operationFactors, int result) {
        MathOperationType operationType = ofNullable(operationFactors.getOperationType())
                .orElseThrow(() -> new InvalidRequestException("Operation type is missing."));

        return operationType.getFuncCalc().apply(operationFactors.getFactorA(), operationFactors.getFactorB()) == result;
    }
}
