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

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Account authenticateAndIssueRefreshToken(String username, String password) {
        Account account = accountRepository.findByUsername(username).orElseThrow(AccountNotFoundException::new);

        //비밀번호 검증
        if(!passwordEncoder.matches(password, account.getPassword())){
            throw new AccountAuthorNotOwnerException();
        }

        //탈퇴 검증
        if(account.getAccountState() == AccountState.INACTIVE){
            throw new AccountAuthorInactiveException();
        }

        //리프레시토큰 갱신
        account.setRefreshToken(UUID.randomUUID().toString());
        account.setRefreshExpiresAt(Instant.now().plus(30, ChronoUnit.DAYS));

        return account;
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
