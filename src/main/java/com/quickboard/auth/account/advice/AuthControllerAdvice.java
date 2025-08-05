package com.quickboard.auth.account.advice;

import com.quickboard.auth.account.controller.AuthController;
import com.quickboard.auth.account.exception.impl.AccountAuthorInactiveException;
import com.quickboard.auth.account.exception.impl.AccountAuthorNotOwnerException;
import com.quickboard.auth.account.exception.impl.AccountNotFoundException;
import com.quickboard.auth.common.advice.ErrorInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthController.class)
@Slf4j
public class AuthControllerAdvice {

    @ExceptionHandler({AccountNotFoundException.class, AccountAuthorInactiveException.class, AccountAuthorNotOwnerException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo loginFailHandler(HttpServletRequest request, Exception e){
        log.info("exception: {}", e.getMessage());
        return ErrorInfo.builder()
                .url(request.getRequestURI())
                .ex(e)
                .build();
    }
}
