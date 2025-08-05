package com.quickboard.auth.common.security.filter;

import com.quickboard.auth.common.constants.JwtConstants;
import com.quickboard.auth.common.security.authentication.JwtAuthentication;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/*
* 나중에 스프링 시큐리티가 제공하는 방식으로 토큰을 다루려면 oauth2-resource-server를 추가하고
* BearerTokenAuthenticationFilter를 사용해서 구현할것
* */

/*
* 그냥 빈으로 등록하면 2번 등록됨 https://docs.spring.io/spring-security/reference/servlet/architecture.html
* */

@RequiredArgsConstructor
@Slf4j
public class JwtSecurityContextPersistenceFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalToken = extractToken(request.getHeader(JwtConstants.TOKEN_HEADER_NAME));
        if (optionalToken.isPresent()) {
            try {
                attemptAuthenticate(optionalToken.get());
            } catch (AuthenticationException e) {
                log.warn("Caught BadCredentialsException: {}", e.getMessage());
                authenticationEntryPoint.commence(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }



    private Optional<String> extractToken(String originalToken){
        if(Objects.isNull(originalToken)){
            return Optional.empty();
        }
        else if (!originalToken.startsWith(JwtConstants.BEARER_WITH_SPACE)) {
            log.info("does not start with '{}'. token = {}", JwtConstants.BEARER_WITH_SPACE, originalToken);
            return Optional.empty();
        }

        return Optional.of(originalToken.substring(JwtConstants.BEARER_WITH_SPACE.length()));
    }

    private void attemptAuthenticate(String token) {
        Authentication authentication = authenticationManager.authenticate(JwtAuthentication.unauthenticated(token));
        SecurityContext context = SecurityContextHolder.createEmptyContext(); //race condition을 피하려면 새로 생성해야함.
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        log.info("Authenticated user = {}", authentication.toString());
    }
}
