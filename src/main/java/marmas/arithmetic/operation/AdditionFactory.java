package marmas.arithmetic.operation;

import lombok.RequiredArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Component;

import static marmas.arithmetic.model.MathOperationType.ADDITION;

@Component
@RequiredArgsConstructor
class AdditionFactory implements OperationFactory {

  private final FactorGenerator factorGenerator;

  @Override
  public OperationFactors getFor(Integer max) {
    int factorA = factorGenerator.generate(max);
    int factorB = factorGenerator.generate(max - factorA);
    return new OperationFactors(factorA, factorB, ADDITION);
  }

  @Override
  public MathOperationType getType() {
    return ADDITION;
  }
}
