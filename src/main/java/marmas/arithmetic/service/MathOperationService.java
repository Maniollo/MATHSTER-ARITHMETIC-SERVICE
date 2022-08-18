package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.operation.FactorGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Deprecated
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
                : MathOperationType.values()[numberGenerator.generate(operationTypes.size() - 1)];
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
        log.info("Generated: {}", factors.getOperationType().getFuncFormat().apply(factors.getFactorA(), factors.getFactorB()));
        return factors;
    }

    public List<OperationFactors> getBulkFactors(List<MathOperationType> operationTypes, int range, int quantity) {
        return IntStream.range(0, quantity)
                .mapToObj(it -> getFactorsFor(operationTypes, range))
                .collect(Collectors.toList());
    }

}
