package com.quickboard.auth.account.utils;

import com.quickboard.auth.account.entity.Account;

public interface TokenMaker {
    String generateAccessToken(Account account);
}
