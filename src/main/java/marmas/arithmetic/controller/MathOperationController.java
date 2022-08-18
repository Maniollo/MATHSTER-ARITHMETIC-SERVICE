package marmas.arithmetic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marmas.arithmetic.model.MathOperationType;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.service.MathOperationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Deprecated
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Api(value = "MathOperationController")
class MathOperationController {
    private final MathOperationService mathOperationService;

    @ApiOperation(value = "Create factors for specified math operation", response = OperationFactors.class)
    @ResponseContract
    @GetMapping(value = "/operation", produces = APPLICATION_JSON_VALUE)
    OperationFactors getFactors(
            @NotNull @NotEmpty @RequestParam List<MathOperationType> operationTypes,
            @RequestParam(required = false, defaultValue = "10") @Min(1) Integer range) {
        return mathOperationService.getFactorsFor(operationTypes.stream().distinct().collect(toList()), range);
    }
}
