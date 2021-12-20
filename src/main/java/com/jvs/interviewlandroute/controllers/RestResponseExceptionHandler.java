package com.jvs.interviewlandroute.controllers;

import com.jvs.interviewlandroute.exceptions.NoPathException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class, NoPathException.class })
    protected ResponseEntity<String> badRequest(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}