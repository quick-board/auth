package com.quickboard.auth.account.service.impl;

import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.exception.impl.AccountAuthorInactiveException;
import com.quickboard.auth.account.exception.impl.AccountAuthorNotOwnerException;
import com.quickboard.auth.account.exception.impl.AccountNotFoundException;
import com.quickboard.auth.account.repository.AccountRepository;
import com.quickboard.auth.account.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final static int REFRESH_TOKEN_EXPIRATION_DAYS = 30;

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Account authenticateAndIssueRefreshToken(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);

        //탈퇴 검증
        if(account.getAccountState() == AccountState.INACTIVE){
            throw new AccountAuthorInactiveException();
        }

        //비밀번호 검증
        if(!passwordEncoder.matches(password, account.getPassword())){
            throw new AccountAuthorNotOwnerException();
        }

        //리프레시토큰 갱신
        renewRefreshToken(account);

        return account;
    }

    @Transactional
    @Override
    public void expireRefreshToken(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("id=" + accountId));
        expireRefreshToken(account);
    }

    @Transactional
    @Override
    public Account rotateRefreshTokenIfValid(String refreshToken) {
        Account account = accountRepository.findByRefreshToken(refreshToken).orElseThrow(() ->
                new AccountNotFoundException("token=" + refreshToken));
        renewRefreshToken(account);

        return account;
    }

    private static void setRefreshTokenAndExpiresAt(Account account, String newToken, Instant newExpiresAt){
        account.setRefreshToken(newToken);
        account.setRefreshExpiresAt(newExpiresAt);
    }

    private static void renewRefreshToken(Account account){
        setRefreshTokenAndExpiresAt(account, UUID.randomUUID().toString(), Instant.now().plus(REFRESH_TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS));
    }

    private static void expireRefreshToken(Account account){
        setRefreshTokenAndExpiresAt(account, null, Instant.now());
    }
}
