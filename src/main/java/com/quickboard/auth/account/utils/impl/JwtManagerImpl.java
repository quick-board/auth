package com.quickboard.auth.account.utils.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.quickboard.auth.common.constants.JwtConstants;
import com.quickboard.auth.account.entity.Account;
import com.quickboard.auth.account.enums.AccountState;
import com.quickboard.auth.account.enums.Role;
import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.feign.dto.ProfileOriginResponse;
import com.quickboard.auth.common.security.dto.AccountDetails;
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

    private final static String PROFILE_ID_CLAIM_NAME = "profile_id";
    private final static String NICKNAME_CLAIM_NAME = "nickname";

    @Override
    public String generateAccessToken(Account account, ProfileOriginResponse profile) {
        Instant now = Instant.now();
        return JWT.create()
                .withSubject(account.getId().toString())
                .withClaim(JwtConstants.ROLE, account.getRole().toString())
                .withClaim(JwtConstants.ACCOUNT_STATE, account.getAccountState().toString())
                .withClaim(PROFILE_ID_CLAIM_NAME, profile.id())
                .withClaim(NICKNAME_CLAIM_NAME, profile.nickname())
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(30, ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    @Override
    public Optional<AuthorizedAccount> validAccessToken(String accessToken) {
        try {
            DecodedJWT decodedJWT = jwtVerifier.verify(accessToken);

            AccountDetails accountDetails = new AccountDetails(
                    Role.from(decodedJWT.getClaim(JwtConstants.ROLE).asString()),
                    AccountState.from(decodedJWT.getClaim(JwtConstants.ACCOUNT_STATE).asString())
            );
            AuthorizedAccount authorizedAccount = new AuthorizedAccount(Long.parseLong(decodedJWT.getSubject()), accountDetails);

            return Optional.of(authorizedAccount);
        }catch (NumberFormatException | JWTVerificationException e){
            log.warn("Invalid JWT token: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
