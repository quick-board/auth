package com.quickboard.auth.account.service;

import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.enums.AccountState;

public interface AccountService {
    Long createAccount(AccountCreate accountCreate);
    void patchAccountState(Long accountId, AccountState state);
    boolean usernameExists(String username);
}
