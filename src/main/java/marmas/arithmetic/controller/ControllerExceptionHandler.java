package marmas.arithmetic.controller;

import marmas.arithmetic.exception.InvalidRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    protected ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, request);
    }
}
