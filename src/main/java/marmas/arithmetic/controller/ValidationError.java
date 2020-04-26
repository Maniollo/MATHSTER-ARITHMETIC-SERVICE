package marmas.arithmetic.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
class ValidationError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<String> errors = new ArrayList<>();

    private final String errorMessage;

    void addValidationError(String error) {
        errors.add(error);
    }
}
