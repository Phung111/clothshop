package com.phung.clothshop.domain.entity.product;

public enum EColor {
    RED,
    BLUE,
    YELLOW,
    GREEN,
    ORANGE,
    BLACK,
    WHITE;

    public String getValue() {
        return this.name();
    }
}
