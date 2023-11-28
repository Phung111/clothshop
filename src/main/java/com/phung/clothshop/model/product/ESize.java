package com.phung.clothshop.model.product;

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
