package com.phung.clothshop.model.customer;

public enum EGender {
    MALE,
    FEMALE,
    OTHER;

    public String getValue() {
        return this.name();
    }
}
