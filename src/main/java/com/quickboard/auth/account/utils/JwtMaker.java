package com.quickboard.auth.account.utils;

import com.quickboard.auth.account.entity.Account;

public interface JwtMaker {
    String generateAccessToken(Account account);
}
