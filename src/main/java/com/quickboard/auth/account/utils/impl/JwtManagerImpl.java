package com.quickboard.auth.account.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.quickboard.auth.account.constants.JwtConstants;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;
import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.security.dto.AuthorizedAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtManagerImpl implements JwtManager {

    @Value("${spring.application.name}")
    private String issuer;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

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

    @Override
    public Optional<AuthorizedAccount> validAccessToken(String accessToken) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);
            AuthorizedAccount authorizedAccount = new AuthorizedAccount(Long.parseLong(decodedJWT.getSubject()),
                    Role.valueOf(decodedJWT.getClaim(JwtConstants.ROLE).asString()),
                    AccountState.valueOf(decodedJWT.getClaim(JwtConstants.ACCOUNT_STATE).asString()));

            return Optional.of(authorizedAccount);
        }catch (NumberFormatException | JWTVerificationException e){
            log.warn("Invalid JWT token: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
