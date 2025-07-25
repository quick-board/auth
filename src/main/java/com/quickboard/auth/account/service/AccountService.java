package com.quickboard.auth.account.service;

import com.quickboard.auth.account.dto.AccountCreate;
import com.quickboard.auth.account.enums.AccountState;

public interface AccountService {
    void createAccount(AccountCreate accountCreate);
    void patchAccountState(Long accountId, AccountState state);
}
