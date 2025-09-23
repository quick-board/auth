package com.quickboard.auth.account.utils;

import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.common.feign.dto.ProfileOriginResponse;
import com.quickboard.auth.common.security.dto.AuthorizedAccount;

import java.util.Optional;

public interface JwtManager {
    String generateAccessToken(Account account, ProfileOriginResponse profile);
    Optional<AuthorizedAccount> validAccessToken(String accessToken);
}
