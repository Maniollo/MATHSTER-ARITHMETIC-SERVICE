package marmas.arithmetic.controller;

import lombok.AllArgsConstructor;
import marmas.arithmetic.model.ResultAttempt;
import marmas.arithmetic.service.ResultAttemptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
class ResultAttemptController {
    private final ResultAttemptService resultAttemptService;

    @PostMapping(value = "/results")
    ResultAttempt verifyResultAttempt(@RequestBody ResultAttempt resultAttempt) {
        return resultAttemptService.verifyResultAttempt(resultAttempt);
    }
}
