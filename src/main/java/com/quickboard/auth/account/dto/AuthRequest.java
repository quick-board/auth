package com.quickboard.auth.account.dto;

public record AuthRequest(
    String username,
    String password
) { }
