package com.quickboard.auth.account.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.quickboard.auth.account.constants.JwtConstants;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.utils.JwtMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtMakerImpl implements JwtMaker {

    @Value("${spring.application.name}")
    private String issuer;
    private final Algorithm algorithm;

    @Override
    public String generateAccessToken(Account account) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(account.getId().toString())
                .withClaim(JwtConstants.ROLE, account.getRole().name().toLowerCase())
                .withClaim(JwtConstants.ACCOUNT_STATE, account.getAccountState().name().toLowerCase())
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(30, ChronoUnit.MINUTES))
                .sign(algorithm);
    }
}
