package com.phung.clothshop.domain.entity.account;

public enum ERole {
    ADMIN,
    USER,
    GUESS;

    public String getValue() {
        return this.name();
    }
}
