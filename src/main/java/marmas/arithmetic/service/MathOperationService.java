package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class MathOperationService {
    private final AdditionFactorsService additionFactorsService;

    public OperationFactors getFactorsFor(MathOperationType operation, int range) {
        OperationFactors factors = additionFactorsService.getFactors(range);
        log.info("[factorA: {}, factorB: {} GENERATED]", factors.getFactorA(), factors.getFactorB());
        return factors;
    }
}
