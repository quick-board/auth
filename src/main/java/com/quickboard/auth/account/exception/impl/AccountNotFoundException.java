package com.quickboard.auth.account.exception.impl;

import com.quickboard.auth.common.exception.ResourceNotFoundException;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException() {
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
