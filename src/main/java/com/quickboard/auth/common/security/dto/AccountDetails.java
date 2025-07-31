package com.quickboard.auth.common.security.dto;

import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;

public record AccountDetails(
        Role role,
        AccountState accountState
) { }
