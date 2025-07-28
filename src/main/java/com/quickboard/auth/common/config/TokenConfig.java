package com.quickboard.auth.common.config;

import com.auth0.jwt.algorithms.Algorithm;
import com.quickboard.auth.common.properties.Hs512Properties;
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
}
