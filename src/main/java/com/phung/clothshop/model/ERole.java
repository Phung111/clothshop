package com.phung.clothshop.model;

public enum ERole {
    ADMIN,
    USER,
    GUESS;

    public String getValue() {
        return this.name();
    }
}
