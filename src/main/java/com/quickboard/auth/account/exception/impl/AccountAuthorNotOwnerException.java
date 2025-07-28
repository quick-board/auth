package com.quickboard.auth.account.exception.impl;

import com.quickboard.auth.account.exception.AccountAuthorException;

public class AccountAuthorNotOwnerException extends AccountAuthorException {
    public AccountAuthorNotOwnerException(Throwable cause) {
        super(cause);
    }

    public AccountAuthorNotOwnerException() {
    }
}
