package com.quickboard.auth.account.exception.impl;

import com.quickboard.auth.common.exception.ResourceCreationException;

public class AccountCreationException extends ResourceCreationException {
    public AccountCreationException() {
    }

    public AccountCreationException(Throwable cause) {
        super(cause);
    }
}
