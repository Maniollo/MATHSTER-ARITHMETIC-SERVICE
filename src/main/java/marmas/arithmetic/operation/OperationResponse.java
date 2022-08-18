package marmas.arithmetic.operation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import marmas.arithmetic.model.OperationFactors;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
class OperationResponse {
  private OperationFactors operations;
}
