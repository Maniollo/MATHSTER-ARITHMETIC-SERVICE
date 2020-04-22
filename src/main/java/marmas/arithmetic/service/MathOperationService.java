package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
@Slf4j
@AllArgsConstructor
public class MathOperationService {
    private final AdditionFactorsService additionFactorsService;
    private final SubtractionFactorsService subtractionFactorsService;
    private final FactorGenerator numberGenerator;

    public OperationFactors getFactorsFor(List<MathOperationType> operationTypes, int range) {

        MathOperationType operation = operationTypes.size() == 1
                ? operationTypes.get(0)
                : MathOperationType.values()[numberGenerator.generate(operationTypes.size()-1)];

        log.info("========= GENERATE FACTORS FOR {} and range {} =========", operation.name(), range);

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
