package com.phung.clothshop.model.product;

public enum ECategory {
    SHIRT,
    PANT,
    SHOE,
    JACKET;

    public String getValue() {
        return this.name();
    }
}
