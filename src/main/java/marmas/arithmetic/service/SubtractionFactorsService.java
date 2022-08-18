package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.operation.FactorGenerator;
import org.springframework.stereotype.Service;

import static marmas.arithmetic.model.MathOperationType.SUBTRACTION;

@Deprecated
@Service
@AllArgsConstructor
public class SubtractionFactorsService {
    private final FactorGenerator factorGenerator;

    public OperationFactors getFactors(int range) {
        int factorA = factorGenerator.generate(range);
        int factorB = factorGenerator.generate(factorA);
        return new OperationFactors(factorA, factorB, SUBTRACTION);
    }
}
