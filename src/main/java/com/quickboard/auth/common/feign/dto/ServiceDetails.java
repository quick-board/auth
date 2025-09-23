package com.quickboard.auth.common.feign.dto;

public record ServiceDetails(
    String serviceName,
    String clientRequestPath
) { }
