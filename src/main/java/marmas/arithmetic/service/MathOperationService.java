package marmas.arithmetic.service;

import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

import static marmas.arithmetic.model.MathOperationType.ADDITION;

@Service
public class MathOperationService {
    public OperationFactors getFactorsFor(MathOperationType operation, int range) {
        return new OperationFactors(0,0, ADDITION);
    }
}
