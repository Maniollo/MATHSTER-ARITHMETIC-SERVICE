package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
@AllArgsConstructor
public class MathOperationService {
    private final AdditionFactorsService additionFactorsService;
    private final SubtractionFactorsService subtractionFactorsService;

    public OperationFactors getFactorsFor(MathOperationType operation, int range) {
        OperationFactors factors;
        switch (operation) {
            case SUBTRACTION:
                factors = subtractionFactorsService.getFactors(range);
                break;
            case ADDITION:
                factors = additionFactorsService.getFactors(range);
                break;
            default:
                throw new UnsupportedOperationException(format("%s operation is unsupported", operation.name()));
        }
        log.info("[factorA: {}, factorB: {} GENERATED]", factors.getFactorA(), factors.getFactorB());
        return factors;
    }

}
