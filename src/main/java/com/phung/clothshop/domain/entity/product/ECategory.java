package com.phung.clothshop.domain.entity.product;

public enum ECategory {
    SHIRT,
    PANT,
    SHOE,
    SANDAL,
    JACKET,
    SKIRT,
    DRESS;


    public String getValue() {
        return this.name();
    }
}
