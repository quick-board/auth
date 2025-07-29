package com.quickboard.auth.common.security.dto;

import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;

public record AuthorizedAccount(
    Long userId,
    Role role,
    AccountState accountState
) { }
