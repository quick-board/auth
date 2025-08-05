package com.quickboard.auth.common.advice;

import com.quickboard.auth.common.exception.AuthorException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(AuthorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo authorExceptionHandling(HttpServletRequest request, Exception e){
        return ErrorInfo.builder()
                .url(request.getRequestURI())
                .ex(e)
                .build();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo authenticationExceptionHandling(HttpServletRequest request, Exception e){
        log.info("request uri = {}, exception message = {}", request.getRequestURI(), e.getMessage());
        return  ErrorInfo.builder()
                .url(request.getRequestURI())
                .ex(e)
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorInfo accessDeniedExceptionHandling(HttpServletRequest request, Exception e){
        log.info("request uri = {}, exception message = {}", request.getRequestURI(), e.getMessage());
        return  ErrorInfo.builder()
                .url(request.getRequestURI())
                .ex(e)
                .build();
    }
}
