package com.quickboard.auth.common.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.quickboard.auth.common.properties.Hs512Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TokenConfig {

    @Bean
    @Profile("hs512")
    public Algorithm algorithm(Hs512Properties hs512Properties){

        return Algorithm.HMAC512(hs512Properties.getKey());
    }

    @Bean
    public JWTVerifier jwtVerifier(Algorithm algorithm,
                                   @Value("${spring.application.name}")String issuer){
        return JWT.require(algorithm)
                .withIssuer(issuer)
                .acceptExpiresAt(5)
                .build();
    }
}
