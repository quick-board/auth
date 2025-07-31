package com.quickboard.auth.common.security.dto;

public record AuthorizedAccount(
    Long userId,
    AccountDetails accountDetails
) { }
