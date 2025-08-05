package com.quickboard.auth.common.advice;

import lombok.Builder;

public class ErrorInfo {
    public final String url;
    public final String ex;

    @Builder
    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }
}