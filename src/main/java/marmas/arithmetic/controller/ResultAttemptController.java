package marmas.arithmetic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import marmas.arithmetic.model.OperationFactors;
import marmas.arithmetic.model.ResultAttempt;
import marmas.arithmetic.service.ResultAttemptService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Api(value = "MathOperationController")
class ResultAttemptController {
    private final ResultAttemptService resultAttemptService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ResultAttempt.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Service Error")
    })
    @ApiOperation(value = "Verify the result attempt and give back the result", nickname = "Verify the result attempt and give back the result")
    @PostMapping(value = "/results")
    ResponseEntity verifyResultAttempt(@Valid @RequestBody ResultAttempt resultAttempt, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        return ResponseEntity.ok(resultAttemptService.verifyResultAttempt(resultAttempt));
    }
}
