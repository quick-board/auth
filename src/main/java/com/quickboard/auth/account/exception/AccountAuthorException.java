package com.quickboard.auth.account.exception;

import com.quickboard.auth.common.exception.AuthorException;

public abstract class AccountAuthorException extends AuthorException {
    public AccountAuthorException(Throwable cause) {
        super(cause);
    }

    public AccountAuthorException() {
    }
}
