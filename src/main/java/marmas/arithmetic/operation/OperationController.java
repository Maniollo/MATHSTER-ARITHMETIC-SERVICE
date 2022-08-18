package marmas.arithmetic.operation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.controller.ResponseContract;
import marmas.arithmetic.controller.ValidationErrorBuilder;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttemptRequest;
import marmas.arithmetic.service.ResultAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(value = "OperationController")
public class OperationController {

  private final OperationService operationService;
  private final ResultAttemptService resultAttemptService;

  @ApiOperation(value = "Create numbers for specified math operation", response = OperationFactors.class)
  @ResponseContract
  @GetMapping(value = "/api/operations", produces = APPLICATION_JSON_VALUE)
  OperationResponse getOperations(@NotNull @NotEmpty @RequestParam List<MathOperationType> types,
                                  @RequestParam(required = false, defaultValue = "10") @Min(10) Integer max) {

    OperationFactors operations = operationService.getRandomFor(types, max);

    return new OperationResponse(operations);
  }

  @ApiOperation(value = "Verify the answer and give back the result", response = Boolean.class)
  @ResponseContract
  @PostMapping(value = "/api/operations", consumes = APPLICATION_JSON_VALUE)
  ResponseEntity verifyResultAttempt(@Valid @RequestBody ResultAttemptRequest resultAttemptRequest, Errors errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
    }

    return ResponseEntity.ok(resultAttemptService.verifyResultAttempt(resultAttemptRequest));
  }
}
