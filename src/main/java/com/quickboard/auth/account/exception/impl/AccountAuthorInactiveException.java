package com.quickboard.auth.account.exception.impl;

import com.quickboard.auth.account.exception.AccountAuthorException;

public class AccountAuthorInactiveException extends AccountAuthorException {
    public AccountAuthorInactiveException(Throwable cause) {
        super(cause);
    }

    public AccountAuthorInactiveException() {
    }
}
