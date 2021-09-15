package com.example.CustomerService.exception;

import com.example.CustomerService.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Error> handleConstraintViolationExceptions(Exception ex) {
        return new ResponseEntity<>(new Error(String.valueOf(HttpStatus.BAD_REQUEST),
                ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleExceptions(Exception ex) {
        return new ResponseEntity<>(new Error(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
                ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
