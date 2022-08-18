package marmas.arithmetic.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
class OperationGenerator {
  private final List<OperationFactory> factories;

  OperationFactors getFor(MathOperationType operation, Integer maxResult) {
    OperationFactors fac = factories.stream()
        .filter(fact -> fact.getType() == operation)
        .findFirst()
        .map(f -> f.getFor(maxResult))
        .orElseThrow(NotSupportedOperationException::new);

    log.debug("Generated operation: {}", fac.getOperationType().getFuncFormat().apply(fac.getFactorA(), fac.getFactorB()));
    return fac;
  }
}
