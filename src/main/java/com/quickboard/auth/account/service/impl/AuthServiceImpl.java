package com.quickboard.auth.account.service.impl;

import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.repository.AccountRepository;
import com.quickboard.auth.account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public Account authenticateAndIssueRefreshToken(String username, String password) {
        return null;
    }

    @Transactional
    @Override
    public boolean expireRefreshToken(Long accountId) {
        return false;
    }

    @Transactional
    @Override
    public Account rotateRefreshTokenIfValid(String refreshToken) {
        return null;
    }
}
