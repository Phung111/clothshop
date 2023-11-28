package com.phung.clothshop.model.product;

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
