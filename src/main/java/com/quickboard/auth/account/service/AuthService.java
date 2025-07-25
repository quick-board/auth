package com.quickboard.auth.account.service;

import com.quickboard.auth.account.entity.Account;

public interface AuthService {
    Account authenticateAndIssueRefreshToken(String username, String password);
    boolean expireRefreshToken(Long accountId);
    Account rotateRefreshTokenIfValid(String refreshToken);
}
