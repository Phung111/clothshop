package com.phung.clothshop.model.product;

public enum EStatus {
    NEW,
    HOLD,
    CLOSED;

    public String getValue() {
        return this.name();
    }
}
