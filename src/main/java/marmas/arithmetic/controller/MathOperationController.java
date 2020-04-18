package marmas.arithmetic.controller;

import lombok.RequiredArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.service.MathOperationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Validated
class MathOperationController {
    private final MathOperationService mathOperationService;

    @GetMapping(value = "/operation", produces = APPLICATION_JSON_VALUE)
    OperationFactors getFactors(
            @RequestParam MathOperationType operationType,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer range) {
        return mathOperationService.getFactorsFor(operationType, range);
    }
}
