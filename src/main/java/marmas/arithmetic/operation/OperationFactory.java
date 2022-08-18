package marmas.arithmetic.operation;

import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;

interface OperationFactory {
  OperationFactors getFor(Integer maxResult);

  MathOperationType getType();
}
