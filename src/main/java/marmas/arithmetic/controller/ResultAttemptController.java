package marmas.arithmetic.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import marmas.arithmetic.model.ResultAttemptRequest;
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

    @ApiOperation(value = "Verify the result attempt and give back the result", response = Boolean.class)
    @ResponseContract
    @PostMapping(value = "/results")
    ResponseEntity verifyResultAttempt(@Valid @RequestBody ResultAttemptRequest resultAttemptRequest, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        return ResponseEntity.ok(resultAttemptService.verifyResultAttempt(resultAttemptRequest));
    }
}
