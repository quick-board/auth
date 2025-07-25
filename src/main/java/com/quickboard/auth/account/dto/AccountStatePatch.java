package com.quickboard.auth.account.dto;

import com.quickboard.auth.account.enums.AccountState;

public record AccountStatePatch(
        AccountState state
) { }
