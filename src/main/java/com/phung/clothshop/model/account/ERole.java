package com.phung.clothshop.model.account;

public enum ERole {
    ADMIN,
    USER,
    GUESS;

    public String getValue() {
        return this.name();
    }
}
