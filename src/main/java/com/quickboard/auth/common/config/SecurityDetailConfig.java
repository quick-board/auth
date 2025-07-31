package com.quickboard.auth.common.config;

import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityDetailConfig {

    @Bean
    public AuthenticationManager authenticationManager(JwtManager jwtManager){
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtManager);
        return new ProviderManager(List.of(jwtAuthenticationProvider));
    }
}
