package com.phung.clothshop.domain.entity.customer;

public enum EGender {
    MALE,
    FEMALE,
    OTHER;

    public String getValue() {
        return this.name();
    }
}
