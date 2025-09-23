package com.quickboard.auth.common.feign.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CallerType {
    ANONYMOUS,
    END_USER,
    SERVICE;

    @JsonCreator
    public static CallerType from(String v){
        String s = v == null ? "" : v.trim().toLowerCase();
        return switch (s) {
            case "anonymous" -> ANONYMOUS;
            case "end_user", "enduser", "end-user", "end user" -> END_USER;
            case "service" -> SERVICE;
            default -> throw new IllegalArgumentException("Invalid callerType: " + v);
        };
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
