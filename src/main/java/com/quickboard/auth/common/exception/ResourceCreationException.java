package com.quickboard.auth.common.exception;

public abstract class ResourceCreationException extends RuntimeException{
    public ResourceCreationException() {
    }

    public ResourceCreationException(Throwable cause) {
        super(cause);
    }
}
