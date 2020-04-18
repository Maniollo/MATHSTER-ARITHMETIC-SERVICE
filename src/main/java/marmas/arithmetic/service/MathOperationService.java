package marmas.arithmetic.service;

import lombok.AllArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MathOperationService {
    private final AdditionFactorsService additionFactorsService;

    public OperationFactors getFactorsFor(MathOperationType operation, int range) {
        return additionFactorsService.getFactors(range);
    }
}
