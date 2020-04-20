package marmas.arithmetic.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.service.MathOperationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@Validated
class MathOperationController {
    private final MathOperationService mathOperationService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OperationFactors.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Service Error")
    })
    @ApiOperation(value = "Create factors for specified math operation", nickname = "Create factors for specified math operation")
    @GetMapping(value = "/operation", produces = APPLICATION_JSON_VALUE)
    OperationFactors getFactors(
            @NotNull  @RequestParam MathOperationType operationType,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer range) {
        return mathOperationService.getFactorsFor(operationType, range);
    }
}
