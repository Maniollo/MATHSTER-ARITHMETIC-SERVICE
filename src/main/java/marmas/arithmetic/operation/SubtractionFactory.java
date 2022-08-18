package marmas.arithmetic.operation;

import lombok.RequiredArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Component;

import static marmas.arithmetic.model.MathOperationType.SUBTRACTION;

@Component
@RequiredArgsConstructor
class SubtractionFactory implements OperationFactory {
  private final FactorGenerator factorGenerator;

  @Override
  public OperationFactors getFor(Integer max) {
    int factorA = factorGenerator.generate(max);
    int factorB = factorGenerator.generate(factorA);
    return new OperationFactors(factorA, factorB, SUBTRACTION);
  }

  @Override
  public MathOperationType getType() {
    return SUBTRACTION;
  }
}
