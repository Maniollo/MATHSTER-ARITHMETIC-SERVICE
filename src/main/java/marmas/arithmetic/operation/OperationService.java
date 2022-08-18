package marmas.arithmetic.operation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {
  private final OperationGenerator generator;
  private final RandomnesProvider randomnesProvider;

  public OperationFactors getRandomFor(List<MathOperationType> operations, int maxResult) {
    validateInputs(operations, maxResult);

    List<MathOperationType> onlyUnique = operations.stream().distinct().collect(toList());
    int idx = randomnesProvider.getRandomNumberFor(onlyUnique.size());
    return generator.getFor(onlyUnique.get(idx), maxResult);
  }

  private void validateInputs(List<MathOperationType> operations, int maxResult) {
    if (isNull(operations) || operations.isEmpty()) {
      throw new MissingOperationException();
    }

    if (maxResult < 1) {
      throw new IncorrectMaxValueException();
    }
  }

  public List<OperationFactors> getRandomNTimesFor(List<MathOperationType> operations, int maxResult, int size) {
    return IntStream.range(0, size)
        .mapToObj(it -> getRandomFor(operations, maxResult))
        .collect(toList());
  }
}
