package com.quickboard.auth.common.feign.dto;

public record AnonymousDetails(
    String guestId,
    String ip
) { }
