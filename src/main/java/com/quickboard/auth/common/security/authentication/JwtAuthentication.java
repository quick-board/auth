package com.quickboard.auth.common.security.authentication;

import com.quickboard.auth.common.security.dto.AccountDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final Long userId;
    private final String credential;

    //인증 전
    public JwtAuthentication(String credential) {
        super(null);
        this.userId = null;
        this.credential = credential;
        super.setAuthenticated(false);
    }

    //인증 후
    public JwtAuthentication(Long userId, AccountDetails accountDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userId = userId;
        this.credential = null;
        super.setDetails(accountDetails);
        super.setAuthenticated(true);
    }



    @Override
    public Object getCredentials() {
        return credential;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    public static JwtAuthentication unauthenticated(String token){
        return new JwtAuthentication(token);
    }

    public static JwtAuthentication authenticated(Long userId, AccountDetails accountDetails, Collection<? extends GrantedAuthority> authorities){
        return new JwtAuthentication(userId, accountDetails, authorities);
    }

}
