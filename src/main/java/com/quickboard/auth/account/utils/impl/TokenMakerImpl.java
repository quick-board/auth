package com.quickboard.auth.account.utils.impl;

import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.utils.TokenMaker;
import org.springframework.stereotype.Component;

@Component
public class TokenMakerImpl implements TokenMaker {

    //todo 토큰 만들기 account를 dto로 바꿀지 고민하기
    @Override
    public String generateAccessToken(Account account) {
        return "";
    }
}
