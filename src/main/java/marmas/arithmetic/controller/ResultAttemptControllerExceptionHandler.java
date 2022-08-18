package marmas.arithmetic.controller;

import marmas.arithmetic.exception.InvalidRequestException;
import marmas.arithmetic.operation.OperationController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice(assignableTypes = {ResultAttemptController.class, OperationController.class})
public class ResultAttemptControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {InvalidRequestException.class})
    protected ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ValidationError error = ValidationErrorBuilder.fromBindingErrors(e.getBindingResult());
        return handleExceptionInternal(e, error, new HttpHeaders(), BAD_REQUEST, request);
    }
}
