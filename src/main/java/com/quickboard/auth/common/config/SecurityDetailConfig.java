package com.quickboard.auth.common.config;

import com.quickboard.auth.account.utils.JwtManager;
import com.quickboard.auth.common.security.provider.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityDetailConfig {

    @Bean
    public AuthenticationManager customAuthenticationManager(JwtManager jwtManager){
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtManager);
        return new ProviderManager(List.of(jwtAuthenticationProvider));
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationManager.class)
    public AuthenticationManager defaultAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
}
