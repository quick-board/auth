package com.quickboard.auth.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("jwt")
@Getter @Setter
@Profile("hs512")
public class Hs512Properties {
    private String key;
}
