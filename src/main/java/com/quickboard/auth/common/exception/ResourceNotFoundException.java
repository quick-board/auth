package com.quickboard.auth.common.exception;

public abstract class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
