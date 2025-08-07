package com.quickboard.auth.account.enums;

public enum AccountState {
    ACTIVE,
    INACTIVE,
    SLEEPING;

    public static AccountState from(String state){
        try {
            return AccountState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid argument: " + state);
        }
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
