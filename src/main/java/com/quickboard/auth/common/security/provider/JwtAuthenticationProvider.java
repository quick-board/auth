package com.quickboard.auth.common.security.provider;

import com.quickboard.auth.account.enums.Role;
import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.constants.SecurityConstants;
import com.quickboard.auth.common.security.authentication.JwtAuthentication;
import com.quickboard.auth.common.security.dto.AuthorizedAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtManager jwtManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        Optional<AuthorizedAccount> optionalAuthorizedAccount = jwtManager.validAccessToken(token);

        //검증 실패
        if(optionalAuthorizedAccount.isEmpty()){
            log.info("invalid token = {}", token);
            throw new BadCredentialsException(token);
        }

        return successfulAuthentication(optionalAuthorizedAccount.get());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }

    private static JwtAuthentication successfulAuthentication(AuthorizedAccount authorizedAccount){

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(
                SecurityConstants.ROLE_PREFIX + Role.from(authorizedAccount.accountDetails().role().toString())));

        return JwtAuthentication.authenticated(
                authorizedAccount.userId(),
                authorizedAccount.accountDetails(),
                authorities);
    }
}
