package com.quickboard.auth.common.feign.dto;

import com.quickboard.auth.common.feign.enums.Gender;

import java.time.Instant;
import java.time.LocalDate;

public record ProfileOriginResponse(
        Long id,
        String nickname,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate birthdate,
        Instant createdAt,
        Instant updatedAt
) {}
