package com.phung.clothshop.domain.entity.product;

public enum ESize {
    S,
    M,
    L,
    XL,
    XXL,
    XXXL;

    public String getValue() {
        return this.name();
    }
}
