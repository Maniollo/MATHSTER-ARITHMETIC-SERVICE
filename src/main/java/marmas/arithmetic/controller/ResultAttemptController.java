package marmas.arithmetic.controller;

import lombok.AllArgsConstructor;
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
class ResultAttemptController {
    private final ResultAttemptService resultAttemptService;

    @PostMapping(value = "/results")
    ResponseEntity verifyResultAttempt(@Valid @RequestBody ResultAttempt resultAttempt, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(ValidationErrorBuilder.fromBindingErrors(errors));
        }

        return ResponseEntity.ok(resultAttemptService.verifyResultAttempt(resultAttempt));
    }
}
