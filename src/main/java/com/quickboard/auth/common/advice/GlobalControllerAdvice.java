package com.quickboard.auth.common.advice;

import com.quickboard.auth.common.exception.AuthorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthorException.class)
    public ResponseEntity<Void> authorExceptionHandler(){
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
