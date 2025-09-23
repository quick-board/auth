package com.quickboard.auth.common.feign.dto;

import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;

public record EndUserDetails(
        Long accountId,
        AccountState accountState,
        Role role,
        Long profileId,
        String nickname
) { }
