package com.quickboard.auth.account.service.impl;

import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;
import com.quickboard.auth.account.exception.impl.AccountCreationException;
import com.quickboard.auth.account.repository.AccountRepository;
import com.quickboard.auth.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void createAccount(AccountCreate accountCreate) {
        Account newAccount = Account.builder()
                .username(accountCreate.username())
                .password(passwordEncoder.encode(accountCreate.password()))
                .accountState(AccountState.ACTIVE)
                .role(Role.USER)
                .build();
        try{
            accountRepository.save(newAccount);
        }catch (Exception e){
            log.warn("exception =\n", e);
            throw new AccountCreationException(e);
        }
    }

    @Transactional
    @Override
    public void patchAccountState(Long accountId, AccountState state) {

    }
}
