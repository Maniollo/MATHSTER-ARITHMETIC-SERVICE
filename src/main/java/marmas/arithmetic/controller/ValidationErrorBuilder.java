package marmas.arithmetic.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

class ValidationErrorBuilder {

    static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
